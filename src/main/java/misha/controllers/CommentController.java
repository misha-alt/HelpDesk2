package misha.controllers;


import misha.dao.CreateCommDAO;
import misha.dao.ManagerDAO;
import misha.dao.TickedDAO;
import misha.dao.UserDAO;
import misha.domain.*;
import misha.service.CreateComment;
import misha.service.FileService;
import misha.service.ManagerService;
import misha.service.UserService;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Transactional

public class CommentController {

    private CreateCommDAO createCommDAO;
    private UserDAO userDAO;
    private DateClass dateClass;
    private ManagerDAO managerDAO;
    private TickedDAO tickedDAO;
    private FileService fileService;
    @Autowired
    public CommentController(CreateCommDAO createCommDAO, UserDAO userDAO, DateClass dateClass, ManagerDAO managerDAO, TickedDAO tickedDAO, FileService fileService) {
        this.createCommDAO = createCommDAO;
        this.userDAO = userDAO;
        this.dateClass = dateClass;
        this.managerDAO = managerDAO;
        this.tickedDAO = tickedDAO;
        this.fileService = fileService;
    }

    @GetMapping("/makeTest")
    public String newTest(Principal principal, Model model) {


        return "test";

    }

    @PostMapping("/comments/{id}")
    public String vievComments(Principal principal, Model model, @PathVariable("id") int id) {
        User user = userDAO.findByEmail(principal.getName());
        Ticked ticked = tickedDAO.geTickedById(id);
        model.addAttribute("tickedComm", ticked.getComments());
        return "comments";
    }


}






