package misha.controllers;

import misha.domain.Comments;
import misha.service.CreateComment;
import misha.service.ManagerService;
import misha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.security.Principal;

@Controller
@Transactional
public class UsersControllers {

    private UserService userService;
    private CreateComment createComment;
    private ManagerService managerService;
    @Autowired
    public UsersControllers(UserService userService, CreateComment createComment, ManagerService managerService) {
        this.userService = userService;
        this.createComment = createComment;
        this.managerService = managerService;
    }

    @GetMapping("/manager")
    public String viewManager(Principal principal, Model model){

        model.addAttribute("ManagerName",userService.getByLogin(principal.getName()).get(0).getFirst_name());
        model.addAttribute("SomeComment", managerService.allComments());


        return "manager";
    }
    @GetMapping("/engineer")
    public String viewEngineer(Principal principal, Model model){

        model.addAttribute("EngineerName",userService.getByLogin(principal.getName()).get(0).getFirst_name());
        model.addAttribute("AllCommentsOfUsers", managerService.allComments());

        return "engineer";
    }

}
