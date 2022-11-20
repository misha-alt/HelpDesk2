package misha.controllers;


import misha.domain.Comments;
import misha.domain.User;
import misha.service.CreateComment;
import misha.service.UserService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;

@Controller
@Transactional

public class CommentController {

   private CreateComment createComment;
    private UserService userService;
    @Autowired
    public CommentController(CreateComment createComment, UserService userService) {
        this.createComment= createComment;
        this.userService = userService;
    }

    @RequestMapping("/login")
    public String login(Principal principal, Model model) {
            return "login";

    }
@RequestMapping("/newCom")
public String newComent(Model model, Principal principal){
    model.addAttribute("comment", new Comments());
    model.addAttribute("userName", userService.getByLogin(principal.getName()));
    return "users";
}
    @GetMapping("/makeTest")
    public String newTest (Principal principal, Model model){
        return "test";
    }
        @RequestMapping("/ert")
        public String upDate(Model model,@ModelAttribute("comment") Comments comments, Principal principal){
            createComment.createComentAndSave(comments);
            createComment.seveUserCmments(comments, principal.getName());
            model.addAttribute("comment", new Comments());
            model.addAttribute("userName", userService.getByLogin(principal.getName()).get(0).getFirst_name());
            model.addAttribute("userComment", userService.getOurCom(principal.getName()));
        return "users";
        }

}
