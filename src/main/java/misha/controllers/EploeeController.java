package misha.controllers;

import misha.dao.EmployeeDAO;
import misha.dao.UserDAO;
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

      model.addAttribute("EmploeeName", userDAO.getByLogin(principal.getName()).get(0).getFirst_name());
      model.addAttribute("allTickedOfEmplee", employeeDAO.allTiscedCreatedByEmployee(userDAO.getByLogin(principal.getName()).get(0).getLogin()));


        return "emploee";
    }

}
