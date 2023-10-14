package misha.service;

import misha.domain.Ticked;
import misha.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class TestManagerService {
   /* @Mock
    private TickedService tickedService;

    @Mock
    private UserService userService;


    @InjectMocks
    private ManagerService managerService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSeveManagerTicked() {
        // Создаем моки и заглушки
        Ticked ticked = new Ticked();
        String name = "John";
        int id =1;
        ticked.setId(id);
        User user = new User();
        List<User> users = new ArrayList<>();
        users.add(user);

        // Задаем поведение моков
        when(userService.getByLogin(name)).thenReturn(users);
        when(managerService.getTickedById(ticked.getId())).thenReturn(ticked);

        // Вызываем метод, который мы хотим протестировать
        managerService.seveManagerTicked(ticked, name);

        // Проверяем, что нужные методы были вызваны с правильными аргументами
        verify(managerService).createTickced(ticked);
        verify(userService).getByLogin(name);
        //verify(commentService).updateUsersComment(user);

        // Проверяем, что пользователь получил нужный билет
        assertTrue(user.getTicked().contains(ticked));
    }*/
}
