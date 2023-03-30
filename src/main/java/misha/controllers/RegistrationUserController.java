package misha.controllers;




import misha.Valdator.MyValidator;
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

private UserService userService;
private FormValidationMeth formValidationMeth;
    @Autowired
    public RegistrationUserController(UserService userService, FormValidationMeth formValidationMeth) {
        this.userService = userService;
        this.formValidationMeth = formValidationMeth;
    }

    @RequestMapping("/regForm")
    public String regUser(Model model){


        model.addAttribute("user", new User());

        return "registration";
    }


    @RequestMapping("/regForm2")
    public String regUser2(User user, BindingResult result, Model model,  Principal principal){
       new MyValidator().validate(user,result);
        if (result.hasErrors()){
            model.addAttribute("user", user);
            return "registration";
        }
        userService.createUser(user);
        return "redirect:/makeTest";

    }

}
