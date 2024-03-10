package misha.controllers;

import misha.Valdator.MyValidator;

import misha.dao.RoleDAO;
import misha.dao.UserDAO;
import misha.domain.Passwords;
import misha.domain.RoleOfUser;
import misha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;


import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@Transactional
public class RegistrationUserController {

    private UserDAO userDAO;

    private RoleDAO roleDAO;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationUserController(UserDAO userDAO, RoleDAO roleDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    //форма для регистрации пользователя
    @RequestMapping("/regForm")
    public String regUser(Model model) {


        model.addAttribute("user", new User());

        return "registration";
    }

    //роверяем заполненную форму
    @RequestMapping("/regForm2")
    public String regUser2(User user, BindingResult result, Model model, Principal principal, @RequestParam("password") String password/*,@RequestParam("authority") String authority*/) {



            new MyValidator().validate(user, result);
            if (result.hasErrors()) {

                model.addAttribute("user", user);
                return "registration";
            } else if (!userDAO.getByLogin(user.getLogin()).isEmpty()) {

                model.addAttribute("message", "username is already in use, please create another one");
                return "registration";
            }
            RoleOfUser roleOfUser = new RoleOfUser();
            roleOfUser.setRole_name("ROLE_USER");
            Set<RoleOfUser> set = new HashSet<>();
            set.add(roleOfUser);
            user.setAuthority(set);


        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        roleDAO.createRole(roleOfUser);
            userDAO.createUser(user);
            return "redirect:/test";

        }

    }

