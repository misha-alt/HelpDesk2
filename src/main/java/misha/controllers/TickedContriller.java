package misha.controllers;

import misha.domain.Ticked;
import misha.domain.User;
import misha.service.CreateComment;
import misha.service.ManagerService;
import misha.service.TickedService;
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
    private TickedService tickedService;
    private CreateComment createComment;

    @Autowired
    public TickedContriller(CreateComment createComment, ManagerService managerService, UserService userService, TickedService tickedService) {
        this.managerService = managerService;
        this.userService = userService;
        this.tickedService = tickedService;
        this.createComment = createComment;

    }

    @RequestMapping("/ticked")
    public ModelAndView create (@RequestParam("name") String name, @RequestParam("id") int id, Principal principal, Model model){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/manager");

        User user = userService.getByLogin(name).get(0);
        user.getTicked().add(tickedService.geTickedById(id));
        createComment.updateUsersComment(user);



        return modelAndView;
    }

}

