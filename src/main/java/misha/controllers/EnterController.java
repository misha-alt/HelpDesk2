package misha.controllers;

import misha.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.security.Principal;

@Controller
@Transactional
public class EnterController {
    private UserDAO userDAO;
    @Autowired
    public EnterController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    @GetMapping("/enterPage")
    public String enterCont(Model model, Principal principal){
        if (userDAO.findByEmail(principal.getName()).getAuthority().equals("ROLE_MANAGER")){
            model.addAttribute("ManagerName",userDAO.findByEmail(principal.getName()).getLogin());
            return "redirect:/manager";
        }
        if(userDAO.findByEmail(principal.getName()).getAuthority().equals("ROLE_ENGINEER")){
            return "redirect:/engineer";
        }
        return "redirect:/test";
    }
}
