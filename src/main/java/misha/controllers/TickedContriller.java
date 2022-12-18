package misha.controllers;

import misha.domain.Ticked;
import misha.service.ManagerService;
import misha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.security.Principal;

@Controller
@Transactional
public class TickedContriller {

    private ManagerService managerService;
    private UserService userService;

    @Autowired
    public TickedContriller(ManagerService managerService, UserService userService) {
        this.managerService = managerService;
        this.userService = userService;

    }
    @RequestMapping("/ticked")
    public ModelAndView create (@RequestParam("name") String name, @RequestParam("id") Long id, Principal principal, Model model){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/manager");
        model.addAttribute("list_of_ticked", userService.getListTicked(principal.getName()));
        model.addAttribute("onleUsersWithComm", managerService.onleUsersWithComments());

        return modelAndView;
    }

}

