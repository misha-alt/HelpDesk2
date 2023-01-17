package misha.controllers;


import misha.service.CreateComment;
import misha.service.ManagerService;
import misha.service.TickedService;
import misha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.security.Principal;

@Controller
@Transactional
public class EngineerController {

    private ManagerService managerService;
    private UserService userService;
    private TickedService tickedService;
    private CreateComment createComment;

    @Autowired
    public EngineerController(CreateComment createComment, ManagerService managerService, UserService userService, TickedService tickedService) {
        this.managerService = managerService;
        this.userService = userService;
        this.tickedService = tickedService;
        this.createComment = createComment;
    }
    @GetMapping("/engineer")
    public String viewEngineer(Principal principal, Model model){

        model.addAttribute("EngineerName",userService.getByLogin(principal.getName()).get(0).getFirst_name());
        model.addAttribute("AllCommentsOfUsers", managerService.allComments());
        model.addAttribute("listOfTicked", userService.getByLogin(principal.getName()).get(0).getTicked());

        return "engineer";
    }

}