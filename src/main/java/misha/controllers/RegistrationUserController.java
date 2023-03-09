package misha.controllers;



import misha.domain.User;
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

private UserService userService;
    @Autowired
    public RegistrationUserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/regForm")
    public String regUser(Model model, Principal principal){


        model.addAttribute("user", new User());

        return "registration";
    }


    @RequestMapping("/regForm2")
    public String regUser2(Model model, @ModelAttribute("user" )@Valid User user, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors())
            return "regForm";

        userService.createUser(user);
        return "redirect:/makeTest";
    }

}
