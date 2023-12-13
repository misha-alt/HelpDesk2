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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@Transactional

public class CommentController {

    private CreateCommDAO createCommDAO;
    private UserDAO userDAO;
    private DateClass dateClass;
    private ManagerDAO managerDAO;
    private TickedDAO tickedDAO;
    private FileService fileService;
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
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

        logger.info("Received request with principal: {}", principal);
        logger.info("Received request with model: {}", model);
        logger.info("Received request with id: {}", id);



        User user = userDAO.findByEmail(principal.getName());//-достаем пользрвателя по Email, по настройкам principal.getName()- возвращает mail
        Ticked ticked = tickedDAO.geTickedById(id);//достаем билет по id
        model.addAttribute("tickedComm", ticked.getComments());
        return "comments";
    }


}






