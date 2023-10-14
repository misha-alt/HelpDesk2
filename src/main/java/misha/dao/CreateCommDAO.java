package misha.dao;

import misha.domain.Comments;
import misha.domain.User;

import java.security.Principal;
import java.util.List;

public interface CreateCommDAO {
    String madeComment ();
    void updateUsersComment (User user);
    void upDateComments(Comments comments);
    void seveUserCmments (Comments comments, String name);
    void createComentAndSave(Comments comments);
    Comments getById(int id);
    List<Comments> getAll();

}
