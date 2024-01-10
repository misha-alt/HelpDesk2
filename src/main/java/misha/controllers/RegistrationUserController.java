package misha.controllers;




import misha.Valdator.MyValidator;
import misha.dao.UserDAO;
import misha.domain.User;
import misha.service.FormValidationMeth;
import misha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import javax.validation.Valid;


import java.io.PrintWriter;
import java.security.Principal;

@Controller
@Transactional
public class RegistrationUserController {

private UserDAO userDAO;
    @Autowired
    public RegistrationUserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    //форма для регистрации пользователя
    @RequestMapping("/regForm")
    public String regUser(Model model){


        model.addAttribute("user", new User());

        return "registration";
    }

    //роверяем заполненную форму
    @RequestMapping("/regForm2")
    public String regUser2(User user, BindingResult result, Model model,  Principal principal){
       new MyValidator().validate(user,result);
        if (result.hasErrors()){

            model.addAttribute("user", user);
            return "registration";
        }else if (!userDAO.getByLogin(user.getLogin()).isEmpty()){

            model.addAttribute("message","username is already in use, please create another one");
            return "registration";
        }
        userDAO.createUser(user);
        return "redirect:/makeTest";

    }

}
