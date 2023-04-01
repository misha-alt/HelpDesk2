package misha.controllers;

import misha.dao.CreateCommDAO;
import misha.dao.ManagerDAO;
import misha.dao.TickedDAO;
import misha.dao.UserDAO;
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

    private UserDAO userDAO;
    private CreateCommDAO createCommDAO;
    private ManagerDAO managerDAO;
    private TickedDAO tickedDAO;
   // private State state;

    @Autowired
    public UsersControllers(UserDAO userDAO, CreateCommDAO createCommDAO, ManagerDAO managerDAO, TickedDAO tickedDAO) {
        this.userDAO = userDAO;
        this.createCommDAO = createCommDAO;
        this.managerDAO = managerDAO;
        this.tickedDAO = tickedDAO;
    }

    /*   @RequestMapping("/login")
        public String login(Principal principal, Model model) {

            return "login";

        }
        @RequestMapping("/login2")
        public String loginMeth(Principal principal, @RequestParam("password") String password, @RequestParam("email") String email) {

            if (userService.selectPassForChec(password).isEmpty()|| userService.selectEmailForChec(email).isEmpty()){
                return "login";
            }
            return  "redirect:/makeTest";


        }
    */
    @RequestMapping("/logout")
    public String logout(){


        return "redirect:/makeTest";
    }


    @RequestMapping("/newCom")
    public String newComent(Model model, Principal principal){
        model.addAttribute("comment", new Comments());
        model.addAttribute("userName", userDAO.getByLogin(principal.getName()));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(new Date());
        model.addAttribute("myDate", dateString);
        model.addAttribute("userComment", userDAO.getByLogin(principal.getName()).get(0).getComments());
        return "users";
    }





    @RequestMapping("/ert")
    public ModelAndView upDate(Model model,@ModelAttribute("comment") Comments comments, Principal principal){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(new Date());
        comments.setDate(dateString);
        createCommDAO.createComentAndSave(comments);
        createCommDAO.seveUserCmments(comments, principal.getName());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/newCom");

        return modelAndView;
    }



    @GetMapping("/manager")
    public String viewManager(Principal principal, Model model, @RequestParam(value = "var", defaultValue = "one") String var, @RequestParam(value = "ob", defaultValue = "NEW") Object ob){

        model.addAttribute("ManagerName",userDAO.getByLogin(principal.getName()).get(0).getFirst_name());
        model.addAttribute("onleUsersWithComm", managerDAO.onleUsersWithComments());
      //model.addAttribute("list2",tickedService.managerAsAppruverAndStateDeclin( userService.getByLogin(principal.getName()).get(0).getLogin()));
     // model.addAttribute("list2",tickedService.sortedlistOfTicked( tickedService.managerAsAppruverAndStateDeclin( userService.getByLogin(principal.getName()).get(0).getLogin())));
        model.addAttribute("list2", tickedDAO.methodForSort(var, principal));
        model.addAttribute("ob", tickedDAO.filteredListByCriteria(ob));


     model.addAttribute("var", userDAO.getByLogin(principal.getName()).get(0).getLogin());
      model.addAttribute("tickedCreatedByManag", userDAO.getByLogin(principal.getName()).get(0).getTicked());

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

}
