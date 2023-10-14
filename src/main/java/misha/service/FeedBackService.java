package misha.service;

import misha.dao.FeedbackDAO;
import misha.domain.FeedBack;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
