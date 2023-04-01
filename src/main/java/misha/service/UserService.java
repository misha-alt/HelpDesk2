package misha.service;

import misha.dao.UserDAO;
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
public class UserService implements UserDAO {



    private SessionFactory sessionFactory;
    private FormValidationMeth formValidationMeth;

    @Autowired
    public UserService(SessionFactory sessionFactory, FormValidationMeth formValidationMeth) {
        this.sessionFactory = sessionFactory;
        this.formValidationMeth= formValidationMeth;
    }
    @Override
    public List<User> getUser (){
        return sessionFactory.getCurrentSession().createQuery("from User").list();

    }


    @Override
    public Object getOurCom(String name){
        return getByLogin(name).get(0).getComments();
    }


    @Override
    public  Object getListTicked(String name){
       /* List<User> users = sessionFactory.getCurrentSession().createQuery("From User").list();
        User userLazyLoaded = users.get(3);
        return (userLazyLoaded.getOrderDetail());*/
       //getByLogin(name).get(0).getTicked();
        return getByLogin(name).get(0).getTicked();
    }


    @Override
    public List<User> getByLogin(String name){
        Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.login = :login");
        query.setParameter("login", name);
        List user = query.list();
            return user;
        }



    @Override
    public User getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }



    @Override
    public void createUser(User user) {
        //проверяем нет ли таких же пароля логна и email в базе


        if (selectPassForChec(user.getPassword()).isEmpty()){
            if (getByLogin(user.getLogin()).isEmpty()){
                if (selectEmailForChec(user.getEmail()).isEmpty()){
                    Session session = sessionFactory.getCurrentSession();
                    session.save(user);
                }
            }

        }
    }
        @Override
        public List selectPassForChec(String password){
        Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.password = :password");
        query.setParameter("password", password);
        return query.list();
        }


        @Override
        public List selectEmailForChec(String email){
            Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.email = :email");
            query.setParameter("email", email);
            return query.list();
        }


}
