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
    /*@Mock
    private Ticked ticked;

    @Mock
    private UserService userService;

    @Mock
    private CreateComment createComment;

    @InjectMocks
    private TickedService tickedService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSeveManagerTicked() {



        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(ticked).thenReturn(new Ticked());

        when(userService.getByLogin(name)).thenReturn(userList);

        tickedService.saveMergeTicked(ticked);

        verify(tickedDao, times(1)).createTickced(ticked);
        verify(userService, times(1)).getByLogin(name);
        verify(userService, times(1)).updateUsersComment(user);
        assertEquals(1, user.getTicked().size());
    }*/
}
