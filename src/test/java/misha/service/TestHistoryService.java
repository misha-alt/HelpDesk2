package misha.service;

import misha.dao.HistoryDAO;
import misha.domain.Tickethistory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestHistoryService {
    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query query;

    @Mock
    private Tickethistory tickethistory;

    @InjectMocks
    private HistoryService historyService;

    @Test
    public void testGetById(){

        tickethistory.setId(1);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from Tickethistory t where t.id = :id")).thenReturn(query);
        when(query.setParameter("id", 1)).thenReturn(query);

        when(query.list()).thenReturn(Arrays.asList(tickethistory));

        Tickethistory tickethistoryTest =  historyService.getById(1);
        assertEquals(tickethistory, tickethistoryTest);
    }
}
