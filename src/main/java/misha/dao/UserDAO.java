package misha.dao;

import misha.domain.FeedBack;
import misha.domain.Ticked;
import misha.domain.User;

import java.util.List;

public interface UserDAO {
    List<User> getUser();
/*
    Object getOurCom(String name);
*/
    Object getListTicked(String name);
    List<User> getByLogin(String name);
    public User getById(Long id);
    void createUser(User user);
    void updateUser(User user);
    List selectPassForChec(String password);
   List <User> selectEmailForChec(String email);

    List<User> findUserByName(String login);
    public User findByEmail(String email);

    void saveOrUpdate(User user);
    void merg(User user);
    void saveUserFeedBack(FeedBack feedBack, String name);


}
