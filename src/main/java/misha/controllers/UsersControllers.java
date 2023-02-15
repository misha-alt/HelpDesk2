package misha.controllers;

import misha.domain.*;
import misha.service.CreateComment;
import misha.service.ManagerService;
import misha.service.TickedService;
import misha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@Transactional
public class UsersControllers {

    private UserService userService;
    private CreateComment createComment;
    private ManagerService managerService;
    private TickedService tickedService;
   // private State state;
    @Autowired
    public UsersControllers(TickedService tickedService, UserService userService, CreateComment createComment, ManagerService managerService) {
        this.userService = userService;
        this.createComment = createComment;
        this.managerService = managerService;
        this.tickedService = tickedService;
       // this.state = state;
    }


    @RequestMapping("/login")
    public String login(Principal principal, Model model) {
        return "login";

    }


    @RequestMapping("/newCom")
    public String newComent(Model model, Principal principal){
        model.addAttribute("comment", new Comments());
        model.addAttribute("userName", userService.getByLogin(principal.getName()));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(new Date());
        model.addAttribute("myDate", dateString);
        model.addAttribute("userComment", userService.getByLogin(principal.getName()).get(0).getComments());
        return "users";
    }





    @RequestMapping("/ert")
    public ModelAndView upDate(Model model,@ModelAttribute("comment") Comments comments, Principal principal){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(new Date());
        comments.setDate(dateString);
        createComment.createComentAndSave(comments);
        createComment.seveUserCmments(comments, principal.getName());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/newCom");

        return modelAndView;
    }



    @GetMapping("/manager")
    public String viewManager(Principal principal, Model model, @RequestParam(value = "var", defaultValue = "one") String var){

        model.addAttribute("ManagerName",userService.getByLogin(principal.getName()).get(0).getFirst_name());
        model.addAttribute("onleUsersWithComm", managerService.onleUsersWithComments());
      //model.addAttribute("list2",tickedService.managerAsAppruverAndStateDeclin( userService.getByLogin(principal.getName()).get(0).getLogin()));
     // model.addAttribute("list2",tickedService.sortedlistOfTicked( tickedService.managerAsAppruverAndStateDeclin( userService.getByLogin(principal.getName()).get(0).getLogin())));
        model.addAttribute("list2", tickedService.methodForSort(var, principal));



     model.addAttribute("var", userService.getByLogin(principal.getName()).get(0).getLogin());
      model.addAttribute("tickedCreatedByManag", userService.getByLogin(principal.getName()).get(0).getTicked());

        return "manager";
    }

    //сортирует по срочности
    @GetMapping("/sotrByUrgense")
    public String forTestFilter(Model model, Principal principal ){

        model.addAttribute("list2", tickedService.sortedlistOfTicked(principal));

        return "testFilter";
    }

//сортирует по ID
    @GetMapping("/sortById")
    public String forTestFilterRedirect(Model model, Principal principal){

        model.addAttribute("list2", tickedService.sortedListById(principal));

        return "testFilter2";
    }

    @GetMapping("/sortByDate")
    public String sortByDate(Model model, Principal principal){

        model.addAttribute("list2", tickedService.sortedByDate(principal));

        return "testFilter3";
    }

}
