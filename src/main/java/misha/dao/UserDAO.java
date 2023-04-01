package misha.dao;

import misha.domain.User;

import java.util.List;

public interface UserDAO {
    List<User> getUser();
    Object getOurCom(String name);
    Object getListTicked(String name);
    List<User> getByLogin(String name);
    public User getById(Long id);
    void createUser(User user);
    List selectPassForChec(String password);
    List selectEmailForChec(String email);
}
