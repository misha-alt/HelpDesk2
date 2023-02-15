package misha.controllers;


import misha.service.*;
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
    private EngineerService engineerService;

    @Autowired
    public EngineerController(CreateComment createComment, EngineerService engineerService, ManagerService managerService, UserService userService, TickedService tickedService) {
        this.managerService = managerService;
        this.userService = userService;
        this.tickedService = tickedService;
        this.createComment = createComment;
        this.engineerService = engineerService;
    }
    @GetMapping("/engineer")
    public String viewEngineer(Principal principal, Model model){

        model.addAttribute("EngineerName",userService.getByLogin(principal.getName()).get(0).getFirst_name());
        model.addAttribute("listOfTicked", engineerService
                .ticketsCreatedByAllEmployeesAndManagersInStatusApproved(userService
                .getByLogin(principal.getName()).get(0).getLogin()));

        return "engineer";
    }

}