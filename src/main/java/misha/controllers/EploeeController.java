package misha.controllers;

import misha.dao.EmployeeDAO;

import misha.dao.TickedDAO;
import misha.dao.UserDAO;
import misha.domain.Passwords;
import misha.domain.RoleOfUser;
import misha.domain.User;
import misha.service.EmpioyeeService;
import misha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


//этот контролер возвращает страницу
@Controller
@Transactional
public class EploeeController {


    private UserDAO userDAO;
    private EmployeeDAO employeeDAO;
    private TickedDAO tickedDAO;

    @Autowired
    public EploeeController(UserDAO userDAO, EmployeeDAO employeeDAO,  TickedDAO tickedDAO) {
        this.userDAO = userDAO;
        this.employeeDAO = employeeDAO;
        this.tickedDAO = tickedDAO;

    }

    @GetMapping("/emploeeContr")
    public String viewEngineer(Principal principal, Model model){
        User user =  userDAO.findByEmail(principal.getName());
      model.addAttribute("EmploeeName", user.getFirst_name());
      model.addAttribute("allTickedOfEmplee", employeeDAO.allTiscedCreatedByEmployee(user.getLogin()));


        return "emploee";
    }

    @RequestMapping("/testRegistration")
    public String testRegViev (Principal principal, Model model){
      User user = userDAO.findByEmail("123");

        model.addAttribute("user",user.getPassword());
       // model.addAttribute("userLOg", user1.getLogin());
        return "testRegistration";
    }

}
