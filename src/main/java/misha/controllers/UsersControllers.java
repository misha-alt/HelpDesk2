package misha.controllers;

import misha.domain.Comments;
import misha.domain.Ticked;
import misha.service.CreateComment;
import misha.service.ManagerService;
import misha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
        model.addAttribute("onleUsersWithComm", managerService.onleUsersWithComments());
       model.addAttribute("ticket", managerService.onleExistsTickets());
        model.addAttribute("list_of_ticked", userService.getListTicked(principal.getName()));


        return "manager";
    }
    @GetMapping("/engineer")
    public String viewEngineer(Principal principal, Model model){

        model.addAttribute("EngineerName",userService.getByLogin(principal.getName()).get(0).getFirst_name());
        model.addAttribute("AllCommentsOfUsers", managerService.allComments());
        model.addAttribute("listOfTicked", userService.getByLogin(principal.getName()).get(0).getTicked());

        return "engineer";
    }

    @RequestMapping("/creation_ticked_view")
    public ModelAndView create (@ModelAttribute("form_ticket") Ticked ticked, Principal principal, Model model){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/manager");
        managerService.createTickced(ticked);
        managerService.seveManagerTicked(ticked, principal.getName());
        model.addAttribute("list_of_ticked", userService.getListTicked(principal.getName()));
        return modelAndView;
    }

}
