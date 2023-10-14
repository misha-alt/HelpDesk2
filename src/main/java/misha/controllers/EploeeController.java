package misha.controllers;

import misha.dao.EmployeeDAO;
import misha.dao.UserDAO;
import misha.domain.User;
import misha.service.EmpioyeeService;
import misha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.security.Principal;

@Controller
@Transactional
public class EploeeController {


    private UserDAO userDAO;
    private EmployeeDAO employeeDAO;
    @Autowired
    public EploeeController(UserDAO userDAO, EmployeeDAO employeeDAO) {
        this.userDAO = userDAO;
        this.employeeDAO = employeeDAO;
    }

    @GetMapping("/emploeeContr")
    public String viewEngineer(Principal principal, Model model){
        User user =  userDAO.findByEmail(principal.getName());
      model.addAttribute("EmploeeName", user.getFirst_name());
      model.addAttribute("allTickedOfEmplee", employeeDAO.allTiscedCreatedByEmployee(user.getLogin()));


        return "emploee";
    }

}
