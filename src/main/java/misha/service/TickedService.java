package misha.service;

import misha.domain.Comments;
import misha.domain.State;
import misha.domain.Ticked;
import misha.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class TickedService {


    private SessionFactory sessionFactory;
    private UserService userService;
    private CreateComment createComment;
    private ManagerService managerService;


    @Autowired
    public TickedService(SessionFactory sessionFactory, UserService
            userService, CreateComment createComment, ManagerService managerService) {
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

        public List<Ticked> getTicketByStatus(String state){
        Query query = sessionFactory.getCurrentSession().createQuery("from Ticked p where p.state = :state");
        List list = query.list();
        return list;
        }
    //все билеты созданный Менеджером и билетв созданные рабочим в статуск "NEW"
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

    public void creationTiket (Ticked ticked, String MyState,
                               String nameOfAssignee, String nameOfApprover, Principal principal){
        State state = State.valueOf(MyState);
        ticked.setState(state);

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

}
