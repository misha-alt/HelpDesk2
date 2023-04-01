package misha.controllers;

import misha.dao.CreateCommDAO;
import misha.dao.ManagerDAO;
import misha.dao.TickedDAO;
import misha.dao.UserDAO;
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

    private ManagerDAO managerDAO;
    private UserDAO userDAO;
    private TickedDAO tickedDAO;
    private CreateCommDAO createCommDAO;
    @Autowired
    public TickedContriller(ManagerDAO managerDAO, UserDAO userDAO, TickedDAO tickedDAO, CreateCommDAO createCommDAO) {
        this.managerDAO = managerDAO;
        this.userDAO = userDAO;
        this.tickedDAO = tickedDAO;
        this.createCommDAO = createCommDAO;
    }

    @RequestMapping("/ticked")
    public ModelAndView create (@RequestParam("name") String name, @RequestParam("id") int id, Principal principal, Model model){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/manager");

        User user = userDAO.getByLogin(name).get(0);
        user.getTicked().add(tickedDAO.geTickedById(id));
        createCommDAO.updateUsersComment(user);

        return modelAndView;
    }

    @RequestMapping("/create_ticket")
    public String ticket (Principal principal, Model model){
        //model.addAttribute("choose_an_engineer", managerService.allEngineers());
        model.addAttribute("form_ticket", new Ticked());
        model.addAttribute("ticket", managerDAO.onleExistsTickets());
        model.addAttribute("assignee", userDAO.getUser());
        model.addAttribute("approver", userDAO.getUser());

        return "create_ticket";
    }

    @RequestMapping("/creation_ticked_view")
    public ModelAndView create (@ModelAttribute("form_ticket") Ticked ticked, Principal principal, Model model
            ,@RequestParam("MyState") String MyState, @RequestParam("nameOfAssignee") String nameOfAssignee,@RequestParam("nameOfApprover")String nameOfApprover
            ,@RequestParam("UrgencyState")String UrgencyState){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/manager");
        tickedDAO.creationTiket(ticked, MyState, UrgencyState, nameOfAssignee, nameOfApprover, principal);


        //model.addAttribute("list2",tickedService.managerAsAppruverAndStateDeclin( userService.getByLogin(principal.getName()).get(0).getLogin()));
       // model.addAttribute("tickedCreatedByManag", userService.getByLogin(principal.getName()).get(0).getTicked());
        //model.addAttribute("tickedCreatedByManag", tickedService.sortedlistOfTicked(principal.getName()));



        return modelAndView;
    }


}

