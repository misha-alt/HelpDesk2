package misha.service;

import misha.dao.HistoryDAO;
import misha.dao.TickedDAO;
import misha.domain.MyFile;
import misha.domain.Ticked;
import misha.domain.Tickethistory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HistoryService implements HistoryDAO {

    private SessionFactory sessionFactory;


    @Autowired
    public HistoryService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

    }

    @Override
    public Tickethistory createRecord(Ticked ticked) {
        Tickethistory ticketHistory = new Tickethistory();
        ticketHistory.setDateHistory(ticked.getCreate_date());

        ticketHistory.setNameHistory(ticked.getName());

        String description = ticked.getName()+" "+ticked.getLoginOfcreater()+" "+ticked.getState()+" "+ticked.getDesireddate()+" "
                +ticked.getUrgency()+" "+"Approver: "+ticked.getApprover()+" "+"Assigneer: "+ticked.getAssignee();

    ticketHistory.setTicket_description(description);

   return ticketHistory;

    }

    @Override
    public void updateRecord(Tickethistory ticketHistory) {
        Session session = sessionFactory.getCurrentSession();
        session.update(ticketHistory);
    }

    @Override
    public void saveRecord(Tickethistory ticketHistory) {
        Session session = sessionFactory.getCurrentSession();
        session.save(ticketHistory);
    }

    @Override
    public Tickethistory showeHistory(int id) {

        Query query = sessionFactory.getCurrentSession().createQuery("from Tickethistory t where t.id = :id");
        query.setParameter("id", id);

        return  (Tickethistory) query.list().get(0);
    }

    @Override
    public Tickethistory grtFortest() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Tickethistory");
        return (Tickethistory) query.list().get(0);
    }

    @Override
    public Tickethistory getById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Tickethistory t where t.id = :id");
        query.setParameter("id", id);
        return (Tickethistory) query.list().get(0);
    }
}
