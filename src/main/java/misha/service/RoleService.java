package misha.service;

import misha.dao.RoleDAO;
import misha.domain.RoleOfUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements RoleDAO {
    private SessionFactory sessionFactory;

    @Autowired
    public RoleService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public void createRole(RoleOfUser roleOfUser) {
        Session session = sessionFactory.getCurrentSession();
        session.save(roleOfUser);
    }
}
