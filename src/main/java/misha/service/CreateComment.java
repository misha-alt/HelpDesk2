package misha.service;


import misha.domain.Comments;

import misha.domain.Ticked;
import misha.domain.User;
import org.dom4j.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Repository
public class CreateComment {

    private SessionFactory sessionFactory;
    private UserService userService;
    @Autowired
    public CreateComment(SessionFactory sessionFactory, UserService userService) {
        this.sessionFactory = sessionFactory;
        this.userService = userService;
    }

    public String madeComment (){
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    public void updateUsersComment (User user){
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }




    public void upDateComments(Comments comments){
        sessionFactory.getCurrentSession().update(comments);
    }



        public void seveUserCmments (Comments comments, String name){

            createComentAndSave(comments);
            User user = userService.getByLogin(name).get(0);
            user.getComments().add( getById(comments.getId()));
            updateUsersComment(user);
        }



        public void createComentAndSave(Comments comments){
            Session session = sessionFactory.getCurrentSession();
            session.save(comments);

        }




    public Comments getById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Comments p where p.id = :id");
        query.setParameter("id", id);
        return (Comments) query.list().get(0);
    }




    public List<Comments> getAll() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Comments");
        return query.list();
    }



  /*  public void testsMet(){

        Comments comments = new Comments();
        comments.setId(1);
        comments.setComment("first Comment");
        comments.setDate("2022-12-12");
        Comments comments2 = new Comments();
        comments2.setId(2);
        comments2.setComment("second Comment");
        comments2.setDate("2022-12-13");
        User user = userService.getOurUser();
        user.getComments().add(comments);
        user.getComments().add(comments2);
    }*/





}
