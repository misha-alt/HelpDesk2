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



    public Object getOurCom(String name){
        return getByLogin(name).get(0).getComments();
    }



    public  Object getListTicked(String name){
       /* List<User> users = sessionFactory.getCurrentSession().createQuery("From User").list();
        User userLazyLoaded = users.get(3);
        return (userLazyLoaded.getOrderDetail());*/
       //getByLogin(name).get(0).getTicked();
        return getByLogin(name).get(0).getTicked();
    }



    public List<User> getByLogin(String name){
        Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.login = :login");
        query.setParameter("login", name);
        List user = query.list();
            return user;
        }




    public User getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }



}
