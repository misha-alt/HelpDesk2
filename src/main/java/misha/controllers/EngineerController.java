package misha.controllers;


import misha.dao.*;
import misha.domain.User;
import misha.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.security.Principal;

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

}