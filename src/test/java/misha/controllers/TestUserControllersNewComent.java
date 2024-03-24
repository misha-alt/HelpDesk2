package misha.controllers;
//import com.sun.javafx.collections.ListListenerHelper;
import misha.dao.TickedDAO;
import misha.dao.UserDAO;
import misha.domain.Comments;
import misha.domain.Ticked;
import misha.domain.User;
import org.junit.Test;
//import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
//@RunWith(MockitoJUnitRunner.class)
public class TestUserControllersNewComent {
    @Mock
    private UserDAO userDAO;

    @Mock
    private TickedDAO tickedDAO;
    @Mock
    private Principal principal;
    @Mock
    private Model model;
    @Mock
    private User user;

    @InjectMocks
    private UsersControllers usersControllers;
 //   @Test
    public void testUserContrNewcom(){

        user.setLogin("John");

        List list = new ArrayList();
        list.add(user);

        Ticked ticked = mock(Ticked.class);

        // Stub method calls
        when(userDAO.findByEmail(principal.getName())).thenReturn(user);
        /*userDAO.getByLogin(user.getLogin()).get(0).getLogin()*/

        when(user.getLogin()).thenReturn("Jhon");
        //when(userDAO.getByLogin(user.getLogin())).thenReturn(list);
        when(userDAO.getByLogin(user.getLogin())).thenReturn(new ArrayList<>());
        when(userDAO.getByLogin(user.getLogin()).get(0).getLogin()).thenReturn("John");




        when(user.getLogin()).thenReturn("John");
        when(tickedDAO.geTickedById(anyInt())).thenReturn(ticked);

        // Call the controller method
        String result = usersControllers.newComent(model, principal, 1);

        // Verify method calls and model attributes
        verify(userDAO).findByEmail(principal.getName());
        verify(tickedDAO).geTickedById(1);

        verify(model).addAttribute("current_ticket", ticked);
        verify(model).addAttribute("comment", new Comments());
        verify(model).addAttribute("userName", userDAO.getByLogin(user.getLogin()).get(0).getLogin());
        verify(model).addAttribute("myDate", anyString());

        // Verify the returned view name
        assertEquals("users", result);
    }

}
