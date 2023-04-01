package misha.service;

import com.google.common.collect.TreeMultiset;
import misha.dao.TickedDAO;
import misha.domain.*;
import misha.test.SomeEnum;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TickedService implements TickedDAO {


    private SessionFactory sessionFactory;
    private UserService userService;
    private CreateComment createComment;
    private ManagerService managerService;

    @Autowired
    public TickedService(SessionFactory sessionFactory, UserService
            userService, CreateComment createComment, ManagerService managerService ) {
        this.sessionFactory = sessionFactory;
        this.userService =userService;
        this.createComment = createComment;
        this.managerService = managerService;

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

        Query query = sessionFactory.getCurrentSession().createQuery
                ("from Ticked t where t.approver = :approver and t.state = 'DECLINED'" +
                                        "or t.approver = :approver and t.state = 'APPROVED'" +
                                        "or t.approver = :approver and t.state = 'CANCELED'" +
                                        "or t.approver = :approver and t.state = 'INPROGRESS'" +
                                        "or t.approver = :approver and t.state = 'DONE'" +
                                        "or t.state = 'NEW' and t.rollOfCreater = 'ROLE_USER'" +
                                        "or t.loginOfcreater = :approver");
        query.setParameter("approver", approver);

        return query.list();
    }
    @Override
    public void creationTiket (Ticked ticked, String MyState, String UrgencyState,
                               String nameOfAssignee, String nameOfApprover, Principal principal){
        State state = State.valueOf(MyState);
        ticked.setState(state);
       Urgency urgency = Urgency.valueOf(UrgencyState);
       ticked.setUrgency(urgency);

        //устанавливаем LoginOfCreater
        ticked.setLoginOfcreater(userService.getByLogin(principal.getName()).get(0).getLogin());

        //устанавливаем провоприемника и одобрителя в билете
        ticked.setAssignee(userService.getByLogin(nameOfAssignee).get(0).getLogin());
        ticked.setApprover(userService.getByLogin(nameOfApprover).get(0).getLogin());
        ticked.setRollOfCreater(userService.getByLogin(principal.getName()).get(0).getAuthority());

        //сохранить созданный тикет в БД
        managerService.createTickced(ticked);
        managerService.seveManagerTicked(ticked, principal.getName());

        //добваить созданный тикет создавшему его ползователю
        User user = userService.getByLogin(principal.getName()).get(0);
        user.getTicked().add(geTickedById(ticked.getId()));

        createComment.updateUsersComment(user);

    }
        @Override
       public  List<Ticked> methodForSort(String var, Principal principal) {

         if (var.equals("one")){
             //sorted ticket by urgency
           return   sortedlistOfTicked(principal);
         }
         if(var.equals("two")){
             //sorted ticket by Id
            return sortedListById(principal);
         }
         if (var.equals("three")){
             //sorted ticket by date
             return sortedByDate(principal);
         }

    return  null;

     }
    @Override
    public  List<Ticked> sortedlistOfTicked(Principal principal){

     List list =managerAsAppruverAndStateDeclin(userService
                .getByLogin(principal.getName()).get(0).getLogin());

     EnumComparator enumComparator = new EnumComparator();

     list.sort(enumComparator);
     return list;

    }
    @Override
    public List<Ticked> sortedListById (Principal principal){

        TicketIdComparator ticketIdComparator = new TicketIdComparator();

        List list =managerAsAppruverAndStateDeclin(userService
                .getByLogin(principal.getName()).get(0).getLogin());

        list.sort(ticketIdComparator);
        return list;
    }
    @Override
    public List<Ticked> sortedByDate(Principal principal)  {
        List list =managerAsAppruverAndStateDeclin(userService
                .getByLogin(principal.getName()).get(0).getLogin());


        DataComp dataComp = new DataComp();
        list.sort(dataComp);
        return list;
    }
    @Override
    public List<Ticked> filteredListByCriteria(Object someCreteria) {

        if (!someCreteria.equals(null)) {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "from Ticked t where t.state = 'APPROVED' and :someCreteria = 'APPROVED'"+
                    "or  t.state = 'CANCELED' and :someCreteria = 'CANCELED'" +
                    "or t.state = 'NEW' and :someCreteria = 'NEW'" +
                            "or t.desireddate = :someCreteria");
            query.setParameter("someCreteria", someCreteria);

            return query.list();
        }
       return null;
    }




}


