package misha.service;

import misha.domain.Comments;
import misha.domain.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.List;

@Service
public class ManagerService {

    private SessionFactory sessionFactory;

    @Autowired
    public ManagerService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<User> allComments(){
        return sessionFactory.getCurrentSession().createQuery("from User").list();
    }



}
