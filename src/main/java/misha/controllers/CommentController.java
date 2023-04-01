package misha.controllers;


import misha.dao.CreateCommDAO;
import misha.dao.ManagerDAO;
import misha.dao.UserDAO;
import misha.domain.Comments;
import misha.domain.DateClass;
import misha.domain.User;
import misha.service.CreateComment;
import misha.service.ManagerService;
import misha.service.UserService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@Transactional

public class CommentController {

    private CreateCommDAO createCommDAO;
    private UserDAO userDAO;
    private DateClass dateClass;
    private ManagerDAO managerDAO;

    public CommentController(CreateCommDAO createCommDAO, UserDAO userDAO, DateClass dateClass, ManagerDAO managerDAO) {
        this.createCommDAO = createCommDAO;
        this.userDAO = userDAO;
        this.dateClass = dateClass;
        this.managerDAO = managerDAO;
    }

    @GetMapping("/makeTest")
    public String newTest (Principal principal, Model model){


        String st1 = "email";
        String st2 = "password";
        if ( userDAO.selectPassForChec(st2).isEmpty()|| userDAO.selectEmailForChec(st1).isEmpty()){
           model.addAttribute("testObject", "All correct");
        }



        model.addAttribute("onleUserWithComment", managerDAO.onleUsersWithComments());

        return "test";
    }
}

    /*@RequestMapping("/newCom")
    public String newComent(Model model, Principal principal){
        model.addAttribute("comment", new Comments());
        model.addAttribute("userName", userService.getByLogin(principal.getName()));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(new Date());
        model.addAttribute("myDate", dateString);
        return "users";
    }*/




