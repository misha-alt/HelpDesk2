package misha.controllers;


import misha.dao.*;
import misha.domain.State;
import misha.domain.Ticked;
import misha.domain.User;
import misha.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

//этот контроллер возвращает страницу инженера
@Controller
@Transactional
public class EngineerController {

    private ManagerDAO managerDAO;
    private UserDAO userDAO;
    private TickedDAO tickedDAO;
    private CreateCommDAO createCommDAO;
    private EngineerDAO engineerDAO;
    @Autowired
    public EngineerController(ManagerDAO managerDAO, UserDAO userDAO, TickedDAO tickedDAO, CreateCommDAO createCommDAO, EngineerDAO engineerDAO) {
        this.managerDAO = managerDAO;
        this.userDAO = userDAO;
        this.tickedDAO = tickedDAO;
        this.createCommDAO = createCommDAO;
        this.engineerDAO = engineerDAO;
    }

    @RequestMapping("/engineer")
    public String viewEngineer(Principal principal, Model model){
        User user = userDAO.findByEmail(principal.getName());
        model.addAttribute("EngineerName",user.getFirst_name());
        model.addAttribute("listOfTicked", engineerDAO
                .ticketsCreatedByAllEmployeesAndManagersInStatusApproved(user.getLogin()));
        model.addAttribute("TestString", "Yes");

        return "engineer";
    }

    @RequestMapping("/tiskedListOfEngeneer")
    public String tiskedListOfEngeneer(Principal principal, Model model,@RequestParam(value = "var", defaultValue = "id") String var){
        User user = userDAO.findByEmail(principal.getName());

        model.addAttribute("newTicked", tickedDAO.getTickedNew());
        model.addAttribute("inPogressTicked", tickedDAO.getTickedInProgress());
        model.addAttribute("doneTicked", tickedDAO.getTickedDone());
        return "tickedListOfEngineer";
    }
        @RequestMapping("/appoint/{id}")
    public String assignControier(Principal principal, @PathVariable("id") int id){
        Ticked ticked = tickedDAO.geTickedById(id);
            User user = userDAO.findByEmail(principal.getName());
        ticked.setAssignee(user.getLogin());
        ticked.setState(State.INPROGRESS);
        tickedDAO.updateTcked(ticked);
        return "redirect:/tiskedListOfEngeneer";

    }

}