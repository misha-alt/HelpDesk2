package misha.controllers;


import misha.domain.Ticked;
import misha.domain.User;
import misha.service.ManagerService;
import misha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.security.Principal;

@Controller
@Transactional
public class ManagersTicketController {


    private ManagerService managerService;
    private UserService userService;
    @Autowired
    public ManagersTicketController(ManagerService managerService, UserService userService) {
        this.managerService = managerService;
        this.userService = userService;
    }

    @RequestMapping("/create_ticket")
    public String ticket (Principal principal, Model model){
       model.addAttribute("choose_an_engineer", managerService.allEngineers());
       model.addAttribute("form_ticket", new Ticked());
       model.addAttribute("ticket", managerService.onleExistsTickets());

        return "create_ticket";
    }




  /*  @RequestMapping("/create_ticket")
    public String createTicket (Principal principal, Model model){
        model.addAttribute("choose_an_engineer", managerService.allEngineers());
        model.addAttribute("ticket", new Ticket ());
        return "create_ticket";
    }*/

}
