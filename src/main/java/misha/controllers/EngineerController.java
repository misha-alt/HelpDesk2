package misha.controllers;


import misha.dao.*;
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

    @GetMapping("/engineer")
    public String viewEngineer(Principal principal, Model model){

        model.addAttribute("EngineerName",userDAO.getByLogin(principal.getName()).get(0).getFirst_name());
        model.addAttribute("listOfTicked", engineerDAO
                .ticketsCreatedByAllEmployeesAndManagersInStatusApproved(userDAO
                .getByLogin(principal.getName()).get(0).getLogin()));

        return "engineer";
    }

}