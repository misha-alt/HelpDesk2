package misha.service;

import misha.dao.FeedbackDAO;
import misha.domain.FeedBack;
import misha.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedBackService implements FeedbackDAO {


    private SessionFactory sessionFactory;
    @Autowired
    public FeedBackService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveRate(FeedBack feedBack) {
        Session session = sessionFactory.getCurrentSession();
        session.save(feedBack);
    }

    @Override
    public void updateRate(FeedBack feedBack) {
        Session session = sessionFactory.getCurrentSession();
        session.update(feedBack);
    }



    @Override
    public FeedBack getAllRate(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from FeedBack u where u.id = :id");
        query.setParameter("id", id);
        FeedBack feedBack = (FeedBack) query.list().get(0);
        return feedBack;
    }
}
