package misha.controllers;

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


    private UserService userService;
    private EmpioyeeService empioyeeService;

    @Autowired
    public EploeeController(UserService userService, EmpioyeeService empioyeeService) {
        this.userService = userService;
        this.empioyeeService = empioyeeService;
    }

    @GetMapping("/engineer")
    public String viewEngineer(Principal principal, Model model){

      model.addAttribute("EmploeeName", userService.getByLogin(principal.getName()).get(0).getFirst_name());
      model.addAttribute("allTickedOfEmplee", empioyeeService.allTiscedCreatedByEmployee(userService.getByLogin(principal.getName()).get(0).getLogin()));


        return "engineer";
    }

}
