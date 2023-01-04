package misha.service;

import misha.domain.Comments;
import misha.domain.Ticked;
import misha.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TickedService {


    private SessionFactory sessionFactory;
    private UserService userService;
    private CreateComment createComment;

    @Autowired
    public TickedService(SessionFactory sessionFactory, UserService
            userService, CreateComment createComment) {
        this.sessionFactory = sessionFactory;
        this.userService =userService;
        this.createComment = createComment;
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

}
