package misha.dao;

import misha.domain.Ticked;
import misha.domain.User;

import java.util.List;

public interface ManagerDAO {
    List<User> allComments();
    List<User>  onleUsersWithComments();
    List<User> allEngineers();
    List<Ticked>  onleExistsTickets();
    List<User> allTicket();
    void seveManagerTicked (Ticked ticked, String name);
    void createTickced (Ticked ticked);
    Ticked getTickedById(int id);

}
