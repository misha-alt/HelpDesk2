package misha.service;


import misha.dao.CreateCommDAO;
import misha.dao.UserDAO;
import misha.domain.Comments;

import misha.domain.Ticked;
import misha.domain.User;
import org.dom4j.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Repository
public class CreateComment implements CreateCommDAO {

    private SessionFactory sessionFactory;
    private UserDAO userDAO;
    @Autowired
    public CreateComment(SessionFactory sessionFactory, UserDAO userDAO) {
        this.sessionFactory = sessionFactory;
        this.userDAO = userDAO;
    }
    @Override
    public String madeComment (){
        LocalDate date = LocalDate.now();
        return date.toString();
    }
    @Override
    public void updateUsersComment (User user){
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }



    @Override
    public void upDateComments(Comments comments){
        sessionFactory.getCurrentSession().update(comments);
    }


        @Override
        public void seveUserCmments (Comments comments, String name){


            User user = userDAO.getByLogin(name).get(0);
            //createComentAndSave(comments);
            user.getComments().add( getById(comments.getId()));
            updateUsersComment(user);
        }


        @Override
        public void createComentAndSave(Comments comments){


            Session session = sessionFactory.getCurrentSession();
            session.save(comments);

        }



    @Override
    public Comments getById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Comments p where p.id = :id");
        query.setParameter("id", id);
        return (Comments) query.list().get(0);
    }



    @Override
    public List<Comments> getAll() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Comments");
        return query.list();
    }


}
