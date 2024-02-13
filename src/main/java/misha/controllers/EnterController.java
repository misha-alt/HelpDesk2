package misha.controllers;

import misha.dao.UserDAO;
import misha.domain.RoleOfUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.security.Principal;


//этот контроллер перенаправляет на другие эндпоинты в зависимости от роли пользователя
@Controller
@Transactional
public class EnterController {
    private UserDAO userDAO;
    private static  final Logger logger = LoggerFactory.getLogger(EnterController.class);

    @Autowired
    public EnterController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    @GetMapping("/enterPage")
    public String enterCont(Model model, Principal principal){

        logger.info("you entered in enterCont");

        for (RoleOfUser authority : userDAO.findByEmail(principal.getName()).getAuthority()) {
            if (authority.getRole_name().equals("ROLE_MANAGER")) {
                return "redirect:/manager";
            }
        }

        for (RoleOfUser authority : userDAO.findByEmail(principal.getName()).getAuthority()) {
            if (authority.getRole_name().equals("ROLE_ENGINEER")) {
                return "redirect:/engineer";
            }
        }

        for (RoleOfUser authority : userDAO.findByEmail(principal.getName()).getAuthority()) {
            if (authority.getRole_name().equals("ROLE_USER")) {
                return "redirect:/emploeeContr";
            }
        }

        return "redirect:/test";
    }
}
