package misha.controllers;

import misha.domain.Comments;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Controller
@Transactional
public class UsersControllers {

    private UserService userService;
    private CreateComment createComment;
    private ManagerService managerService;
    private TickedService tickedService;
   // private State state;
    @Autowired
    public UsersControllers(TickedService tickedService, UserService userService, CreateComment createComment, ManagerService managerService) {
        this.userService = userService;
        this.createComment = createComment;
        this.managerService = managerService;
        this.tickedService = tickedService;
       // this.state = state;
    }

    @GetMapping("/manager")
    public String viewManager(Principal principal, Model model){

        model.addAttribute("ManagerName",userService.getByLogin(principal.getName()).get(0).getFirst_name());
        //model.addAttribute("SomeComment", managerService.allComments());
        model.addAttribute("onleUsersWithComm", managerService.onleUsersWithComments());
       // model.addAttribute("ticket", managerService.onleExistsTickets());
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
    public ModelAndView create (@ModelAttribute("form_ticket") Ticked ticked, Principal principal, Model model
            , @RequestParam("MyState") String MyState, @RequestParam("nameOfAssignee") String nameOfAssignee,@RequestParam("nameOfApprover")String nameOfApprover){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/manager");

        //устанавливаем статус тикета
        State state = State.valueOf(MyState);
        ticked.setState(state);


        //устанавливаем LoginOfCreater
        ticked.setLoginOfcreater(userService.getByLogin(principal.getName()).get(0).getLogin());

        //устанавливаем провоприемника и одобрителя в билете
        ticked.setAssignee(userService.getByLogin(nameOfAssignee).get(0).getLogin());
        ticked.setApprover(userService.getByLogin(nameOfApprover).get(0).getLogin());
        ticked.setRollOfCreater(userService.getByLogin(principal.getName()).get(0).getAuthority());

        //сохранить созданный тикет в БД
        managerService.createTickced(ticked);
        managerService.seveManagerTicked(ticked, principal.getName());

        //добваить созданный тикет создавшему его ползователю
        User user = userService.getByLogin(principal.getName()).get(0);
        user.getTicked().add(tickedService.geTickedById(ticked.getId()));

        createComment.updateUsersComment(user);

        model.addAttribute("tickedCreatedByManag", userService.getByLogin(principal.getName()).get(0).getTicked());



        model.addAttribute("list_of_ticked", userService.getListTicked(principal.getName()));
        return modelAndView;
    }

}
