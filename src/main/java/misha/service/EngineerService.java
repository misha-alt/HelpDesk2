package misha.service;

import misha.dao.EngineerDAO;
import misha.domain.Ticked;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EngineerService implements EngineerDAO {

    private SessionFactory sessionFactory;
    private UserService userService;
    private CreateComment createComment;

    @Autowired
    public EngineerService(SessionFactory sessionFactory, UserService
            userService, CreateComment createComment) {
        this.sessionFactory = sessionFactory;
        this.userService =userService;
        this.createComment = createComment;
    }


    @Override
    public List<Ticked> ticketsCreatedByAllEmployeesAndManagersInStatusApproved(String login){

        Query query = sessionFactory.getCurrentSession().createQuery
                ("from Ticked t where t.rollOfCreater in ('ROLE_USER', 'ROLE_MANAGER') and t.state in ('APPROVED') "+
                        " or t.assignee = :login and t.state in('INPROGRESS', 'DONE')");
        query.setParameter("login", login);

        /*"from Ticked t where t.rollOfCreater in ('ROLE_USER', 'ROLE_MANAGER') and t.state in ('APPROVED') "+
         " or t.assignee = :login"*/



        /*"from Ticked t where t.rollOfCreater in ('ROLE_USER', 'ROLE_MANAGER') " +
                        "and t.state in ('INPROGRESS', 'DONE') " +
                                "or t.assignee = :loginOfcreater " +
                                "or t.login = :loginOfcreater"*/

        return query.list();
    }

}
/*"from Ticked t where t.rollOfCreater in ('ROLE_USER', 'ROLE_MANAGER') " +
        "and t.state in ('INPROGRESS', 'DONE') " +
        "or t.rollOfCreater = 'ROLE_MANAGER' and t.assignee = :loginOfcreater"*/


/*"from Ticked t where t.rollOfCreater = 'ROLE_USER' and t.state = 'APPROVED'" +
        "or t.rollOfCreater = 'ROLE_MANAGER' and t.state = 'APPROVED'" +
        "or t.assignee = :loginOfcreater and t.state = 'INPROGRESS'" +
        "or t.assignee = :loginOfcreater and t.state = 'DONE'"*/


     /*   "SELECT t FROM Ticked t WHERE t.state IN ('APPROVED', 'INPROGRESS', 'DONE')" +
        "AND (t.rollOfCreater = 'ROLE_USER' " +
        "OR t.rollOfCreater = 'ROLE_MANAGER' " +
        "OR t.rollOfCreater = 'ROLE_USER' " +
        "OR t.assignee = :loginOfcreater)"*/
