package misha.controllers;

import misha.dao.CreateCommDAO;
import misha.dao.TickedDAO;
import misha.dao.UserDAO;
import misha.domain.Ticked;
import misha.domain.User;
import misha.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;
//не используется
@Controller
@Transactional
public class DraftController {

    private UserDAO userDAO;
    private TickedDAO tickedDAO;
    private FileService fileService;
    private CreateCommDAO createCommDAO;


    @Autowired
    public DraftController(UserDAO userDAO, TickedDAO tickedDAO, FileService fileService,CreateCommDAO createCommDAO) {
        this.userDAO = userDAO;
        this.tickedDAO = tickedDAO;
        this.fileService = fileService;
        this.createCommDAO = createCommDAO;
    }
        @GetMapping("/draft")
    public String draftView(Principal principal, Model model){

       User user = userDAO.findByEmail(principal.getName());
       List list = tickedDAO.getMyDraft(user.getLogin());
       if (list.isEmpty()){
           model.addAttribute("draftList_message", "no drafts");
       }
       model.addAttribute("draftList",tickedDAO.getMyDraft(user.getLogin()));

        return "draft";
    }

    @GetMapping("/getForm")
    public String editDraftForm(HttpServletRequest request, Model model){
        Ticked ticked = tickedDAO.geTickedById(1);

        model.addAttribute("form_ticket",ticked.getLoginOfcreater());

        return "05-02-2024";
    }
    @GetMapping("/testSQL")
    public String updateAndShow(Model model){

       //model.addAttribute("allFiles", tickedDAO.getAllTicked().get(0));
        model.addAttribute("allComments", createCommDAO.getAll().get(0).getComment());

        return "testSQL";
    }
    @PostMapping("/deleteTicket/{id}")
    public String deleteTicket(@PathVariable("id")int id, Principal principal){

        User user = userDAO.findByEmail(principal.getName());
        Ticked ticked = tickedDAO.geTickedById(id);

        user.getTicked().remove(ticked);
        ticked.getUser().remove(user);
        tickedDAO.deleteTicket(id);
        userDAO.updateUser(user);

        return "redirect:/tickedLis";
    }
    //-----------------------------------------------
@GetMapping("/showTicket")
    public String showUseTicket(Model model,Principal principal){

      List<User> user =  userDAO.findUserByName("Pan");

       model.addAttribute("showUser",user.get(0).getTicked());
       model.addAttribute("showTicked", user.get(0).getLogin());
        return "draft";
    }

    //-----------------------------------------------

@GetMapping("/getIt")
    public String getIt(Model model, Principal principal) throws UnsupportedEncodingException {

      Ticked ticked = tickedDAO.geTickedById(1);

    User user = userDAO.findByEmail(principal.getName());

        model.addAttribute("someKey", ticked.getName());
        return "temperedTestForEntity";
}
}
