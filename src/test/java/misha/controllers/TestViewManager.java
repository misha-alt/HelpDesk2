package misha.controllers;

import misha.dao.ManagerDAO;
import misha.dao.UserDAO;
import misha.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestViewManager {


        @Mock
        private UserDAO userDAO;

        @Mock
        private ManagerDAO managerDAO;

        @Mock
        private Principal principal;

        @Mock
        private Model model;

        @InjectMocks
        private UsersControllers usersControllers;


      @Test
        public void testViewManager() {
          // Установка моковых значений
          String username = "testuser";
          List<User> users = new ArrayList<>();
          User user = new User();
          user.setLogin("John");
          users.add(user);

          //определяем поведение моков
          when(userDAO.findByEmail(principal.getName())).thenReturn(user);

          // Вызов метода контроллера
          String viewName = usersControllers.viewManager(principal, model);

          // Проверка добавления атрибута в модель
          verify(model).addAttribute("ManagerName", "John");

          // Проверка возвращаемого имени представления
          assertEquals("manager", viewName);

        }
    }
