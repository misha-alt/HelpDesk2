package misha.service;

import com.google.common.collect.TreeMultiset;
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
public class TickedService {


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

    public Ticked geTickedById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Ticked p where p.id = :id");
        query.setParameter("id", id);
        return (Ticked) query.list().get(0);
    }

    public List<Ticked> getTickedByUrgency(Urgency urgency){
        Query query = sessionFactory.getCurrentSession().createQuery("from Ticked p where p.urgency =:urgency");
        query.setParameter("urgency", urgency);
       List list= query.list();
       return list;
    }

        public List<Ticked> getTicketByStatus(String state){
        Query query = sessionFactory.getCurrentSession().createQuery("from Ticked p where p.state = :state");
        List list = query.list();
        return list;
        }
    //все билеты созданный юзером
    public List<Ticked> listOfTickedCurrentUser(String login){

        Query query = sessionFactory.getCurrentSession().createQuery
                ("from Ticked t where t.loginOfcreater = :login");
        query.setParameter("login", login);
        return query.list();

    }
    // билеты в статусах  DECLINED, APPROVED, CANCELED, INPROGRESS,DONE  и в которых менеждер как утверждающй
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

    public  List<Ticked> sortedlistOfTicked(Principal principal){

     List list =managerAsAppruverAndStateDeclin(userService
                .getByLogin(principal.getName()).get(0).getLogin());

     EnumComparator enumComparator = new EnumComparator();

     list.sort(enumComparator);
     return list;

    }

    public List<Ticked> sortedListById (Principal principal){

        TicketIdComparator ticketIdComparator = new TicketIdComparator();

        List list =managerAsAppruverAndStateDeclin(userService
                .getByLogin(principal.getName()).get(0).getLogin());

        list.sort(ticketIdComparator);
        return list;
    }

    public List<Ticked> sortedByDate(Principal principal)  {
        List list =managerAsAppruverAndStateDeclin(userService
                .getByLogin(principal.getName()).get(0).getLogin());


        DataComp dataComp = new DataComp();
        list.sort(dataComp);
        return list;
    }

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

    /* public  List<Ticked> methodForSort(String var, Principal principal) {

 DRAFT("DRAFT"),
    NEW("NEW"),
    APPROVED("APPROVED"),
    DECLINED("DECLINED"),
    INPROGRESS("INPROGRESS"),
    DONE("DONE"),
    CANCELED("CANCELED");



      "or t.state = 'APPROVED' and :someCreteria = 'APPROVED'" +
                            "or t.state = 'DECLINED' and :someCreteria = 'DECLINED'" +
                            "or t.state = 'INPROGRESS' and :someCreteria = 'INPROGRESS'" +
                            "or t.state = 'DONE' and :someCreteria = 'DONE'" +
                            "or t.state = 'CANCELED' and :someCreteria = 'CANCELED'"

"or t.urgency = :someCreteria" +

t.state = 'NEW' and :someCreteria = 'NEW'

"from Ticked t where t.name = :someCreteria" +
                        "or t.desireddate = :someCreteria" +
                        "or t.urgency = :someCreteria" +
                        "or t.state = :someCreteria"



          String s = userService.getByLogin(principal.getName()).get(0).getLogin();


           if (s== userService.getByLogin(principal.getName()).get(0).getLogin()){
              return sortedlistOfTicked(managerAsAppruverAndStateDeclin(userService
                       .getByLogin(principal.getName()).get(0).getLogin()));
                   }
           else if(s != userService.getByLogin(principal.getName()).get(0).getLogin()){
               return sortedListById(managerAsAppruverAndStateDeclin(userService
                       .getByLogin(principal.getName()).get(0).getLogin()));
           }

           else return  sortedListById(managerAsAppruverAndStateDeclin(userService
                 .getByLogin(principal.getName()).get(0).getLogin()));




     }*/



}


   /* public  TreeSet<Ticked> sortedlistOfTicked(List<Ticked> list){
        //  Set<Ticked> st = userService.getByLogin(login).get(0).getTicked();
        // List <Ticked> arr = new ArrayList<>(list);
        TreeSet<Ticked> multiset = new TreeSet<>(new EnumComparator());
        multiset.addAll(list);
        return multiset;

    }*/


  /*Set<Ticked> set = new TreeSet<>(Comparator.comparing(Ticked::toString));
        set.addAll(Arrays.asList(ticked.getUrgency())); // [4, 1, 2, 3]*/
// [1, 2, 3, 4]
      /*  Object[] tr = {};
       tr = st.toArray(new Object[st.size()]);
     */
       /* Set<Urgency> set = new TreeSet<>(Comparator.comparing(Urgency::toString));
        set.addAll(Arrays.asList(ticked.getUrgency())); // [4, 1, 2, 3]
        // [1, 2, 3, 4]*/
       /* Set<Urgency> set = new TreeSet<>(Comparator.comparing(Urgency::toString));
        set.addAll(Arrays.asList(ticked.getUrgency())); // [4, 1, 2, 3]
        System.out.println(set); // [1, 2, 3, 4]*/