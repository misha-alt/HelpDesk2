package misha.service;

import misha.domain.Ticked;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EngineerService {

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



    public List<Ticked> ticketsCreatedByAllEmployeesAndManagersInStatusApproved(String loginOfcreater){

        Query query = sessionFactory.getCurrentSession().createQuery
                ("from Ticked t where t.rollOfCreater = 'ROLE_USER' and t.state = 'APPROVED'" +
                        "or t.rollOfCreater = 'ROLE_MANAGER' and t.state = 'APPROVED'" +
                        "or t.assignee = :loginOfcreater and t.state = 'INPROGRESS'" +
                        "or t.assignee = :loginOfcreater and t.state = 'DONE'");
        query.setParameter("loginOfcreater", loginOfcreater);


        return query.list();
    }
}
