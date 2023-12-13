package misha.controllers;

import misha.dao.CreateCommDAO;
import misha.dao.ManagerDAO;
import misha.dao.TickedDAO;
import misha.dao.UserDAO;
import misha.domain.*;
import misha.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@Transactional

public class UsersControllers {

    private UserDAO userDAO;
    private CreateCommDAO createCommDAO;
    private ManagerDAO managerDAO;
    private TickedDAO tickedDAO;
    private UserDetailsServiceImpl userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(UsersControllers.class);
   // private ServletContext servletContext;



   // private State state;

    @Autowired
    public UsersControllers(UserDAO userDAO, CreateCommDAO createCommDAO, ManagerDAO managerDAO, TickedDAO tickedDAO, UserDetailsServiceImpl userDetailsService) {
        this.userDAO = userDAO;
        this.createCommDAO = createCommDAO;
        this.managerDAO = managerDAO;
        this.tickedDAO = tickedDAO;
        this.userDetailsService = userDetailsService;

    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username and password");
        }

        return "login";

    }

    @RequestMapping("/login")
    public String processLogin(@RequestParam("username")String username
            , @RequestParam("password") String password
            , HttpSession session) {

        return "redirect:/manager";

    }

    @RequestMapping("/logout")
    public String logout(){


        return "redirect:/makeTest";
    }

    @RequestMapping("/newCom/{id}")
    public String newComent(Model model, Principal principal, @PathVariable("id") int id){
        User user= userDAO.findByEmail(principal.getName());
        model.addAttribute("current_ticket",tickedDAO.geTickedById(id));
        model.addAttribute("comment", new Comments());
        model.addAttribute("userName", userDAO.getByLogin(user.getLogin()).get(0).getLogin());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(new Date());
        model.addAttribute("myDate", dateString);
        return "users";
    }

    @RequestMapping("/ert/{id}")
    public ModelAndView upDate(Model model,@ModelAttribute("comment") Comments comments, Principal principal, @PathVariable("id") int id){
        User user = userDAO.findByEmail(principal.getName());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(new Date());
        comments.setDate(dateString);

        //устанавливаем кометарию логин создателя
        comments.setLoginOfCreator(user.getLogin());

        //сохраняем коментарии
        createCommDAO.createComentAndSave(comments);
        Ticked ticked= tickedDAO.geTickedById(id);
        ticked.getComments().add(createCommDAO.getById(comments.getId()));
        tickedDAO.updateTcked(ticked);//обновляет билет после добовления коментариев

        createCommDAO.seveUserCmments(comments, user.getLogin());//обновляет пользователя после  добовлени коментариев
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/tickedLis");

        return modelAndView;
    }

    @RequestMapping("/manager")
    public String viewManager(Principal principal, Model model){
        model.addAttribute("ManagerName",userDAO.findByEmail(principal.getName()).getLogin());


        return "manager";
    }

    //сортирует по срочности
    @GetMapping("/sotrByUrgense")
    public String forTestFilter(Model model, Principal principal ){

        model.addAttribute("list2", tickedDAO.sortedlistOfTicked(principal));

        return "testFilter";
    }

    //сортирует по ID
    @GetMapping("/sortById")
    public String forTestFilterRedirect(Model model, Principal principal){

        model.addAttribute("list2", tickedDAO.sortedListById(principal));

        return "testFilter2";
    }

    @GetMapping("/sortByDate")
    public String sortByDate(Model model, Principal principal){

        model.addAttribute("list2", tickedDAO.sortedByDate(principal));

        return "testFilter3";
    }

    @GetMapping("/test")
    public String testContr(HttpServletRequest request, Model model, Principal principal){
        model.addAttribute("request", request);
        logger.info("method test worked successfully");
        return "test";
    }

}


