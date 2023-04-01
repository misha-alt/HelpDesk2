package misha.service;


import misha.dao.EmployeeDAO;
import misha.domain.Ticked;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmpioyeeService implements EmployeeDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public EmpioyeeService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public List<Ticked> allTiscedCreatedByEmployee (String login){
        Query query = sessionFactory.getCurrentSession().createQuery
                ("from Ticked t where t.loginOfcreater = :login");
        query.setParameter("login", login);
        return query.list();
    }

}