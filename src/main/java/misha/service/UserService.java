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


import javax.transaction.Transactional;
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


    /*@Override
    public Object getOurCom(String name){
        return getByLogin(name).get(0).getComments();
    }*/


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

      Session session = sessionFactory.getCurrentSession();
      session.save(user);
     }


    @Override
    public void updateUser(User  user) {
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    public void saveOrUpdate(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    @Override
        public List selectPassForChec(String password){
        Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.password = :password");
        query.setParameter("password", password);
        return query.list();
        }


        @Override
        public List<User> selectEmailForChec(String email){
            Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.email = :email");
            query.setParameter("email", email);
            List <User>  user =  query.list();
            return user;
        }

        @Override
        public List<User> findUserByName(String login) {
        Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.login = :login");
        query.setParameter("login", login);
       List user =query.list();
        return user;
    }

    @Override

    public User findByEmail(String email) {
        Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.email = :email");
        query.setParameter("email", email);
        List <User>  list =  query.list();
        User user = list.get(0);
        return user;
    }

    @Override
    public List<Ticked> getMyDraft(String name) {

        Query query = sessionFactory.getCurrentSession().createQuery("from Ticked t where t.loginOfcreater = :name and t.state = 'DRAFT'");
        query.setParameter("name", name);
        List<Ticked> list = query.list();
        return list;
    }

}
