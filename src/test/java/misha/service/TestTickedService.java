package misha.service;

import misha.dao.TickedDAO;
import misha.domain.State;
import misha.domain.Ticked;
import misha.domain.Urgency;
import misha.domain.User;
import misha.service.TickedService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import sun.security.krb5.internal.Ticket;

import javax.sql.DataSource;
import javax.swing.tree.RowMapper;

import static org.junit.Assert.assertEquals;


import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestTickedService {

    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;

    @Mock
    private Query query;

    @Mock
    private Ticked expectedTicket;

    @Mock
    private Principal principal;

    @InjectMocks
    private TickedService ticketService;

    @InjectMocks
    private UserService userService;


    @Test
    public void TestRequest() throws Exception{

        expectedTicket.setId(1);
        expectedTicket.setAssignee("General");
        expectedTicket.setDescription("Test ticket");

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from Ticked p where p.id = :id")).thenReturn(query);
        when(query.setParameter("id", 1)).thenReturn(query);
        when(query.list()).thenReturn(Arrays.asList(expectedTicket));

        Ticked actualTicket = ticketService.geTickedById(1);

        assertEquals(expectedTicket.getId(), actualTicket.getId());
        assertEquals(expectedTicket.getAssignee(), actualTicket.getAssignee());
        assertEquals(expectedTicket.getDescription(), actualTicket.getDescription());

        }

    }

