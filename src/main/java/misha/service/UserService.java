package misha.service;

import misha.domain.Comments;
import misha.domain.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {



    private SessionFactory sessionFactory;

    @Autowired
    public UserService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<User> getUser (){
        return sessionFactory.getCurrentSession().createQuery("from User").list();

    }

    public User getOurUser(){

        User users = getUser().get(0);
        return users;
    }

    public List<User> getByLogin(String name){
        Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.login = :login");
        query.setParameter("login", name);
        List user = query.list();

            return user;
        }




}
