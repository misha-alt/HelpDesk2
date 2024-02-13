package misha.controllers;


import misha.dao.ManagerDAO;
import misha.dao.UserDAO;
import misha.domain.Ticked;
import misha.domain.User;
import misha.service.ManagerService;
import misha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.security.Principal;

@Controller
@Transactional
public class ManagersTicketController {


    private ManagerDAO managerDAO;
    private UserDAO userDAO;
    @Autowired
    public ManagersTicketController(ManagerDAO managerDAO, UserDAO userDAO) {
        this.managerDAO = managerDAO;
        this.userDAO = userDAO;
    }


}
