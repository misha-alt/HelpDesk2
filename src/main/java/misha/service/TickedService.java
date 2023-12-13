package misha.service;

import misha.dao.*;
import misha.domain.*;
import misha.domain.comparator.*;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TickedService implements TickedDAO {


    private SessionFactory sessionFactory;
    private UserService userService;
    private CreateComment createComment;
    private ManagerService managerService;
    private UserDAO userDAO;
    private EngineerDAO engineerDAO;
    private EmployeeDAO employeeDAO;
    private HistoryDAO historyDAO;


    @Autowired

    public TickedService(SessionFactory sessionFactory, UserService userService, CreateComment createComment, ManagerService managerService, UserDAO userDAO, EngineerDAO engineerDAO, EmployeeDAO employeeDAO, HistoryDAO historyDAO) {
        this.sessionFactory = sessionFactory;
        this.userService = userService;
        this.createComment = createComment;
        this.managerService = managerService;
        this.userDAO = userDAO;
        this.engineerDAO = engineerDAO;
        this.employeeDAO = employeeDAO;
        this.historyDAO = historyDAO;
    }


    @Override
    public Ticked geTickedById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Ticked p where p.id = :id");
        query.setParameter("id", id);
        return (Ticked) query.list().get(0);
    }
    @Override
    public List<Ticked> getTickedByUrgency(Urgency urgency){
        Query query = sessionFactory.getCurrentSession().createQuery("from Ticked p where p.urgency =:urgency");
        query.setParameter("urgency", urgency);
       List list= query.list();
       return list;
    }
        @Override
        public List<Ticked> getTicketByStatus(String state){
        Query query = sessionFactory.getCurrentSession().createQuery("from Ticked p where p.state = :state");
        List list = query.list();
        return list;
        }
    //все билеты созданный юзером
    @Override
    public List<Ticked> listOfTickedCurrentUser(String login){

        Query query = sessionFactory.getCurrentSession().createQuery
                ("from Ticked t where t.loginOfcreater = :login");
        query.setParameter("login", login);
        return query.list();

    }
    // билеты в статусах  DECLINED, APPROVED, CANCELED, INPROGRESS,DONE  и в которых менеждер как утверждающй
    @Override
    public List<Ticked> managerAsAppruverAndStateDeclin(String approver){
        CopareById copareById = new CopareById();
        Query query = sessionFactory.getCurrentSession().createQuery
                ("from Ticked t where t.approver = :approver and t.state = 'DECLINED'" +
                        "or t.approver = :approver and t.state = 'APPROVED'" +
                        "or t.approver = :approver and t.state = 'CANCELED'" +
                        "or t.approver = :approver and t.state = 'INPROGRESS'" +
                        "or t.approver = :approver and t.state = 'DONE'" +
                        "or t.state = 'NEW'" +
                        "or t.loginOfcreater = :approver");
        query.setParameter("approver", approver);


      /*  FROM Ticked t WHERE t.approver = :approver AND (
                t.state IN ('DECLINED', 'APPROVED', 'CANCELED', 'INPROGRESS', 'DONE')
        OR t.state = 'NEW' AND t.rollOfCreater = 'ROLE_USER'
        OR t.loginOfcreater = :approver)*/


        return query.list();
    }
    @Override
    public void creationTiket (Ticked ticked, String cateorySelect, String MyState, String UrgencyState,
                               String nameOfAssignee, String nameOfApprover,String engineerSuccessorr, Principal principal){

       //присваеваем переменые пришедшие в метод полям объекта ticked
        Categor categor = Categor.valueOf(cateorySelect);
        State state = State.valueOf(MyState);
        ticked.setCategor(categor);
        ticked.setState(state);
        Urgency urgency = Urgency.valueOf(UrgencyState);
        ticked.setUrgency(urgency);

        //устанавливаем LoginOfCreater логин создателя
        User user = userDAO.findByEmail(principal.getName());
        ticked.setLoginOfcreater(user.getLogin());

        //если инженер выбрал в поле выбора правоприемника не пустое поле то делаем этого инженера
        // правоприемиком
        if (!engineerSuccessorr.equals("no assignee")){
            ticked.setAssignee(user.getLogin());
        }

        //устанавливаем одобрителя в билете

        List<String> listOfstateForAprover = Arrays.asList("APPROVED", "DECLINED", "DONE");
        if (listOfstateForAprover.contains(MyState)){
            ticked.setApprover(user.getLogin());
        }

        ticked.setRollOfCreater(userService.getByLogin(user.getLogin()).get(0).getAuthority());

        //устанавливаем дату создания билета
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new Date());
        ticked.setCreate_date(currentDate);



        //сохранить созданный тикет и историю в БД
        //managerService.createTickced(ticked);
        //обновление юзера в базе которому добавлен билет
        saveTicked(ticked);
        managerService.seveManagerTicked(ticked, user.getLogin());

    }

    @Override
    public void installChange(String cateorySelect, Ticked ticked, int id) {

        Categor categor = Categor.valueOf(cateorySelect);
        ticked.setCategor(categor);

       /* Set<Tickethistory> ticketHistory = new HashSet<>();
        if (ticked.getTickethistories() != null){

        for (Integer historyId : setOfHistoryId) {
            Tickethistory tickethistory = historyDAO.showeHistory(historyId);
            ticketHistory.add(tickethistory);
        }

            historyDAO.createRecord(ticked,ticketHistory);

    }else {ticketHistory.add(historyDAO.createRecord(ticked));}*/

        updateTcked(ticked);
    }

    @Override
    public void editDrafTicked(Ticked ticked, String engineerSuccessorr) {
       /* User user = userDAO.findByEmail(principal.getName());
        if (!engineerSuccessorr.equals("no assignee")){
            ticked.setAssignee(user.getLogin());
        }*/
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new Date());
        ticked.setCreate_date(currentDate);
        saveOrUpdate(ticked);
    }
//методы для сортирови билетов____________________________________________________________________
    @Override
       public  List<Ticked> methodForSort(String var, Principal principal) {

         if (var.equals("urgency")){
             //sorted ticket by urgency
           return   sortedlistOfTicked(principal);
         }
         if(var.equals("id")){
             //sorted ticket by Id increase
            return sortedListById(principal);
         }
         //sorted ticket by Id decreasing
         if(var.equals("id decreasing")){
             return sortedListByIdDecreasing(principal);
         }
         if (var.equals("date")){
             //sorted ticket by date
             return sortedByDate(principal);
         }
         if(var.equals("creationDate")){
             return sortedByCreationDate(principal);
         }
        if (var.equals("alphabet")){
            //sorted ticket by alphabet
            return sortedByAlphabet(principal);
        }
        if (var.equals("state")){
            //sorted ticket by alphabet
            return sortedByState(principal);
        }
         filteredListByCriteria(var);

    return  null;

     }

    @Override
    public  List<Ticked> sortedlistOfTicked(Principal principal){
     User user = userDAO.findByEmail(principal.getName());
     EnumComparator enumComparator = new EnumComparator();
     if(user.getAuthority().equals("ROLE_MANAGER")){
         List list =managerAsAppruverAndStateDeclin(user.getLogin());
         list.sort(enumComparator);
         return list;
     }
        if(user.getAuthority().equals("ROLE_ENGINEER")){
            List list= engineerDAO.ticketsCreatedByAllEmployeesAndManagersInStatusApproved(user.getLogin());
            list.sort(enumComparator);
            return list;
        }
        List list= userDAO.getByLogin(user.getLogin());
        list.sort(enumComparator);
        return list;

    }

    @Override
    public List<Ticked> sortedListById (Principal principal){
        User user = userDAO.findByEmail(principal.getName());
        TicketIdComparator ticketIdComparator = new TicketIdComparator();
        if(user.getAuthority().equals("ROLE_MANAGER")){
            List list =managerAsAppruverAndStateDeclin(user.getLogin());
            list.sort(ticketIdComparator);
            return list;
        }
        if(user.getAuthority().equals("ROLE_ENGINEER")){
            List list= engineerDAO.ticketsCreatedByAllEmployeesAndManagersInStatusApproved(user.getLogin());
            list.sort(ticketIdComparator);
            return list;
        }
        if(user.getAuthority().equals("ROLE_USER")){
            List list= employeeDAO.allTiscedCreatedByEmployee(user.getLogin());
            list.sort(ticketIdComparator);
            return list;
        }
        List list= userDAO.getByLogin(user.getLogin());
        return list;
    }


    @Override
    public List<Ticked> sortedListByIdDecreasing(Principal principal) {
        User user = userDAO.findByEmail(principal.getName());
        TicketIdComparator ticketIdComparator = new TicketIdComparator();
        if(user.getAuthority().equals("ROLE_MANAGER")){
            List list =managerAsAppruverAndStateDeclin(user.getLogin());
            list.sort(ticketIdComparator.reversed());
            return list;
        }
        if(user.getAuthority().equals("ROLE_ENGINEER")){
            List list= engineerDAO.ticketsCreatedByAllEmployeesAndManagersInStatusApproved(user.getLogin());
            list.sort(ticketIdComparator.reversed());
            return list;
        }
        if(user.getAuthority().equals("ROLE_USER")){
            List list= employeeDAO.allTiscedCreatedByEmployee(user.getLogin());
            list.sort(ticketIdComparator.reversed());
            return list;
        }
        List list= userDAO.getByLogin(user.getLogin());
        return list;
    }

    @Override
    public List<Ticked> sortedByDate(Principal principal)  {
        User user = userDAO.findByEmail(principal.getName());
        DataComp dataComp = new DataComp();
        if(user.getAuthority().equals("ROLE_MANAGER")){
            List list =managerAsAppruverAndStateDeclin(user.getLogin());
            list.sort(dataComp);
            return list;
        }
        if(user.getAuthority().equals("ROLE_ENGINEER")){
            List list= engineerDAO.ticketsCreatedByAllEmployeesAndManagersInStatusApproved(user.getLogin());
            list.sort(dataComp);
            return list;
        }
        if(user.getAuthority().equals("ROLE_USER")){
            List list= employeeDAO.allTiscedCreatedByEmployee(user.getLogin());
            list.sort(dataComp);
            return list;
        }
        List list= userDAO.getByLogin(user.getLogin());
        return list;
    }

    @Override
    public List<Ticked> sortedByCreationDate(Principal principal) {
        User user = userDAO.findByEmail(principal.getName());
        CreateDateCompare createDateCompare = new CreateDateCompare();

        if(user.getAuthority().equals("ROLE_MANAGER")){
            List list =managerAsAppruverAndStateDeclin(user.getLogin());
            list.sort(createDateCompare);
            List subList= list.subList(0,5);
            return subList;
        }
        if(user.getAuthority().equals("ROLE_ENGINEER")){
            List list= engineerDAO.ticketsCreatedByAllEmployeesAndManagersInStatusApproved(user.getLogin());
            list.sort(createDateCompare);
            List subList= list.subList(0,5);
            return subList;
        }
        if(user.getAuthority().equals("ROLE_USER")){
            List list= employeeDAO.allTiscedCreatedByEmployee(user.getLogin());
            list.sort(createDateCompare);
            List subList= list.subList(0,5);
            return list;
        }
        List list= userDAO.getByLogin(user.getLogin());
        return list;

    }

    @Override
    public List<Ticked> filteredListByCriteria(String var) {

        if (!var.equals(null)) {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "from Ticked t where t.state = :var");
            query.setParameter("var", var);

            return query.list();
        }
       return null;
    }

    @Override
    public List<Ticked> sortedByAlphabet(Principal principal) {


        User user = userDAO.findByEmail(principal.getName());
        AlphabetComparator alphabetComparator = new AlphabetComparator();

        if(user.getAuthority().equals("ROLE_MANAGER")){
            List list =managerAsAppruverAndStateDeclin(user.getLogin());
            list.sort(alphabetComparator);

            return list;
        }
        if(user.getAuthority().equals("ROLE_ENGINEER")){
            List list= engineerDAO.ticketsCreatedByAllEmployeesAndManagersInStatusApproved(user.getLogin());
            list.sort(alphabetComparator);

            return list;
        }
        if(user.getAuthority().equals("ROLE_USER")){
            List list= employeeDAO.allTiscedCreatedByEmployee(user.getLogin());
            list.sort(alphabetComparator);

            return list;
        }
        return null;

    }

    @Override
    public List<Ticked> sortedByState(Principal principal) {
        User user = userDAO.findByEmail(principal.getName());
        StateComparator stateComparator = new StateComparator();

        if(user.getAuthority().equals("ROLE_MANAGER")){
            List list =managerAsAppruverAndStateDeclin(user.getLogin());
            list.sort(stateComparator);

            return list;
        }
        if(user.getAuthority().equals("ROLE_ENGINEER")){
            List list= engineerDAO.ticketsCreatedByAllEmployeesAndManagersInStatusApproved(user.getLogin());
            list.sort(stateComparator);

            return list;
        }
        if(user.getAuthority().equals("ROLE_USER")){
            List list= employeeDAO.allTiscedCreatedByEmployee(user.getLogin());
            list.sort(stateComparator);

            return list;
        }
        return null;
    }

    //________________________________________________________________________________________
    @Override
    public List <Ticked> getByName(String name) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Ticked p where p.name = :name");
        query.setParameter("name", name);
        List ticked = query.list();
        return ticked;
    }

    @Override
    public void saveTicked(Ticked ticked) {
        sessionFactory.getCurrentSession().save(ticked);
    }
    @Override
    public void updateTcked(Ticked ticked) {
        sessionFactory.getCurrentSession().update(ticked);
    }
    @Override
    public void saveOrUpdate(Ticked ticked) {
        sessionFactory.getCurrentSession().saveOrUpdate(ticked);
    }

    @Override
    public void deleteTicket(int id) {
        sessionFactory.getCurrentSession().delete(geTickedById(id));
    }

    @Override
    public void saveMergeTicked(Ticked ticked) {
        sessionFactory.getCurrentSession().merge(ticked);
    }
    /*
"from Ticked t where t.approver = :approver and t.state = 'DECLINED'" +
        "or t.approver = :approver and t.state = 'APPROVED'" +
        "or t.approver = :approver and t.state = 'CANCELED'" +
        "or t.approver = :approver and t.state = 'INPROGRESS'" +
        "or t.approver = :approver and t.state = 'DONE'" +
        "or t.state = 'NEW' and t.rollOfCreater = 'ROLE_USER'" +
        "or t.loginOfcreater = :approver"+
        "ORDER BY t.id DESC"
*/
/*" from Ticked t" +
        "where t.approver = :approver and (" +
        "  t.state in ('DECLINED', 'APPROVED', 'CANCELED', 'INPROGRESS', 'DONE')" +
        "  or (t.state = 'NEW' and t.rollOfCreater = 'ROLE_USER')" +
        "  or t.loginOfcreater = :approver)" +
        "order by t.id desc"*/

    @Override
    public List<Ticked> getAllTicked() {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "from Ticked");


        return query.list();
    }
}


