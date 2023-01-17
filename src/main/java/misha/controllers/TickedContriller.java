package misha.controllers;

import misha.domain.State;
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

    @RequestMapping("/create_ticket")
    public String ticket (Principal principal, Model model){
        //model.addAttribute("choose_an_engineer", managerService.allEngineers());
        model.addAttribute("form_ticket", new Ticked());
        model.addAttribute("ticket", managerService.onleExistsTickets());
        model.addAttribute("assignee", userService.getUser());
        model.addAttribute("approver", userService.getUser());

        return "create_ticket";
    }

    @RequestMapping("/creation_ticked_view")
    public ModelAndView create (@ModelAttribute("form_ticket") Ticked ticked, Principal principal, Model model
            , @RequestParam("MyState") String MyState, @RequestParam("nameOfAssignee") String nameOfAssignee,@RequestParam("nameOfApprover")String nameOfApprover){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/manager");
        tickedService.creationTiket(ticked, MyState, nameOfAssignee, nameOfApprover, principal);

        //model.addAttribute("list2",tickedService.managerAsAppruverAndStateDeclin( userService.getByLogin(principal.getName()).get(0).getLogin()));
        model.addAttribute("tickedCreatedByManag", userService.getByLogin(principal.getName()).get(0).getTicked());
        model.addAttribute("list_of_ticked", userService.getListTicked(principal.getName()));
        return modelAndView;
    }


}

