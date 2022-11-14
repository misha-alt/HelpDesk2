package misha.controllers;


import misha.domain.Comments;
import misha.domain.User;
import misha.service.CreateComment;
import misha.service.UserService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;

@Controller
@Transactional

public class CommentController {


   private CreateComment createComment;
    private UserService userService;
    @Autowired
    public CommentController(CreateComment createComment, UserService userService) {

        this.createComment= createComment;
        this.userService = userService;
    }





@GetMapping("/newCom")
public String newComent(Model model){

    model.addAttribute("comment", new Comments());
    model.addAttribute("userName", userService.getOurUser().getFirst_name());

    return "users";
}

    @GetMapping("/makeTest")
    public String newTest (Principal principal, Model model){

       // createComment.testsMet();
        model.addAttribute("userName", userService.getByLogin(principal.getName()).get(0).getFirst_name());

       // model.addAttribute("userComment", userService.getOurUser().getComments());
        //model.addAttribute("fuckName",createComment.getByUser_Id(userService.getOurUser().getId()));
      //  model.addAttribute("getAllComments", userService.getOurUser().getComments());
      //  model.addAttribute("userComment", userService.getOurUser().getComments());

        return "test";
    }

   /* @GetMapping("/newCom")
    public String edit ( Model model){
       User user  = userService.getOurUser();
        model.addAttribute("user",user);
        model.addAttribute("userName", userService.getOurUser().getFirst_name());
        return "users";

    }*/

        @GetMapping("/ert")
        public String upDate(Model model,@ModelAttribute("comment") Comments comments){
       // createComment.seveUserCmments(comments);
            createComment.createComentAndSave(comments);
            createComment.seveUserCmments(comments);
            model.addAttribute("comment", new Comments());
            model.addAttribute("userName", userService.getOurUser().getFirst_name());
           /* model.addAttribute("getComment", userService.getOurUser().getComments().get(0).getComment());
            model.addAttribute("getDate", userService.getOurUser().getComments().get(0).getDate());*/
            model.addAttribute("userComment", userService.getOurUser().getComments());
        return "users";
        }

   /* @PatchMapping("/PostErt")
    public String post(Model model, @ModelAttribute("comment") Comments comments){
        createComment.seveUserCmments(comments);

        return "redirect:/ert";
    }*/


}
