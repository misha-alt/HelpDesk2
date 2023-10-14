package misha.service;

import misha.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestUserService {
    //Ниже приведен пример теста, который использует mockito для проверки метода getByLogin:


        @Mock
        private SessionFactory sessionFactory;

        @Mock
        private Session session;

        @Mock
        private Query query;
        @Mock
        private User user;

      /*  @Mock
        private List <User> expectUser;*/

        @InjectMocks
        private UserService userService;

        @Test
        public void testGetByLogin() throws Exception {
            List<User> expectUser= new ArrayList<>();
           user.setId(1);
           user.setLogin("Man");
          expectUser.add(user);

            when(sessionFactory.getCurrentSession()).thenReturn(session);
            when(session.createQuery("from User u where u.login = :login")).thenReturn(query);
            when(query.setParameter("login", "Man")).thenReturn(query);

            when(query.list()).thenReturn(Arrays.asList(expectUser));

           List <User> users= userService.getByLogin("Man");
            assertEquals(expectUser, users.get(0));

        }
    }
