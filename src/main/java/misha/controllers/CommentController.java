package misha.controllers;


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

    private CreateComment createComment;
    private UserService userService;
    private DateClass dateClass;
    private ManagerService managerService;

    @Autowired
    public CommentController(CreateComment createComment, UserService userService, DateClass dateClass, ManagerService managerService) {
        this.createComment = createComment;
        this.userService = userService;
        this.dateClass = dateClass;
        this.managerService = managerService;
    }

    @GetMapping("/makeTest")
    public String newTest (Principal principal, Model model){


        String st1 = "email";
        String st2 = "password";
        if ( userService.selectPassForChec(st2).isEmpty()|| userService.selectEmailForChec(st1).isEmpty()){
           model.addAttribute("testObject", "All correct");
        }



        model.addAttribute("onleUserWithComment", managerService.onleUsersWithComments());

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




