package misha.controllers;

import misha.Valdator.MyValidator;
import misha.Valdator.ValidatorTickedCreation;
import misha.dao.*;
import misha.domain.*;
import misha.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Nullable;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes("ticked")
@Transactional
/*@MultipartConfig*/
public class TickedContriller {

    private ManagerDAO managerDAO;
    private UserDAO userDAO;
    private TickedDAO tickedDAO;
    private CreateCommDAO createCommDAO;
    private FileService fileService;
    private HistoryDAO historyDAO;
    @Autowired
    public TickedContriller(ManagerDAO managerDAO, UserDAO userDAO, TickedDAO tickedDAO, CreateCommDAO createCommDAO, FileService fileService, HistoryDAO historyDAO) {
        this.managerDAO = managerDAO;
        this.userDAO = userDAO;
        this.tickedDAO = tickedDAO;
        this.createCommDAO = createCommDAO;
        this.fileService = fileService;
        this.historyDAO = historyDAO;
    }

    @ModelAttribute("ticked")
    public Ticked getTicked() {
        return new Ticked();
    }

    @RequestMapping("/ticked")
    public ModelAndView create (@RequestParam("name") String name, @RequestParam("id") int id, Principal principal, Model model){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/manager");

        User user = userDAO.getByLogin(name).get(0);
        user.getTicked().add(tickedDAO.geTickedById(id));
        createCommDAO.updateUsersComment(user);

        return modelAndView;
    }

    @RequestMapping("/create_ticket")
    public String ticket (HttpServletRequest request, Principal principal, Model model){

        //User user =userDAO.findByEmail(principal.getName());
        model.addAttribute("request", request);
        //model.addAttribute("choose_an_engineer", managerService.allEngineers());
        model.addAttribute("form_ticket", new Ticked());
        /*model.addAttribute("comment", new Comments());*/
        model.addAttribute("ticket", managerDAO.onleExistsTickets());
        model.addAttribute("assignee", userDAO.getUser());
        model.addAttribute("approver", userDAO.getUser());

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new Date());

        model.addAttribute("currentDate", currentDate);




        return "create_ticket";
    }

    @RequestMapping("/creation_ticked_view")
    public ModelAndView create (HttpSession session,@ModelAttribute("form_ticket") Ticked ticked, BindingResult result, Principal principal, Model model
            , @RequestParam("cateorySelect")String cateorySelect
            , @RequestParam("MyState") String MyState, @RequestParam(value = "nameOfAssignee",required = false) String nameOfAssignee
            , @RequestParam(value = "nameOfApprover",required = false) String nameOfApprover
            , @RequestParam("UrgencyState")String UrgencyState
            , @RequestParam(value = "engineerSuccessorr", defaultValue = "no assignee") String engineerSuccessorr)  {


        Set<String> validStates = new HashSet<>(Arrays.asList("NEW", "APPROVED", "DECLINE","INPROGRESS","DONE"));
        new ValidatorTickedCreation().validate(ticked,result);

        ModelAndView modelAndView2 = new ModelAndView();
        modelAndView2.setViewName("create_ticket");
        if (result.hasErrors()&&validStates.contains(MyState)){
            model.addAttribute("form_ticket", ticked);

            return modelAndView2;
        }else if (!tickedDAO.getByName(ticked.getName()).isEmpty()){
            return modelAndView2;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/tickedLis");
        tickedDAO.creationTiket(ticked, cateorySelect, MyState, UrgencyState, nameOfAssignee, nameOfApprover,  engineerSuccessorr, principal);


        Tickethistory tickethistory= historyDAO.createRecord(ticked);
         historyDAO.saveRecord(tickethistory);

         Tickethistory tickethistory1 = historyDAO.getById(tickethistory.getId());
         Ticked ticked1 = tickedDAO.geTickedById(ticked.getId());
            Set<Tickethistory> set = new HashSet<>();

            set.add(tickethistory1);
         ticked1.setTickethistories(set);
         tickedDAO.updateTcked(ticked1);



        // fileService.saveFile(file_name);
        return modelAndView;
    }

    @RequestMapping("/tickedLis")
    public String ticketList(Principal principal, Model model, @RequestParam(value = "var", defaultValue = "id") String var){

        User user = userDAO.findByEmail(principal.getName());
        model.addAttribute("list2", tickedDAO.methodForSort(var, principal));

        return "ticketList";
    }

    @PostMapping("/tickedShow/{id}")
    public String tiskedShow(HttpServletRequest request, @PathVariable("id") int id, Model model, Principal principal){

                Ticked ticked = tickedDAO.geTickedById(id);
                User user = userDAO.findByEmail(principal.getName());

            model.addAttribute("ourTicked", ticked);
            model.addAttribute("coments",ticked.getComments());
            model.addAttribute("attached_file", ticked.getMyFile());
            model.addAttribute("request", request);

            model.addAttribute("loginOfcreater", ticked.getLoginOfcreater());
        model.addAttribute("state", ticked.getState());

            model.addAttribute("userLogin", user.getLogin());

                return "tiskedShow";
    }

    @RequestMapping("/addFile/{id}")
    public String addFileCintroller(Model model, @PathVariable("id")int id){
           model.addAttribute("current_id", tickedDAO.geTickedById(id));

         return "testAddFile";
    }


    @RequestMapping("/showFile/{id}")
    public String saveAndShowFile( @RequestParam("file") MultipartFile file, @PathVariable("id") int id, Model model) throws IOException {

        String fileName = file.getOriginalFilename();
        String extension = "";

        if (fileName != null && !fileName.isEmpty()&&file.getSize()<=5242880) {
            int dotIndex = fileName.lastIndexOf('.');
            if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                extension = fileName.substring(dotIndex + 1);
                //список возможных расширений
                Set<String> allowedExtensions = new HashSet<>(Arrays.asList("txt", "pdf", "doc", "docx", "png", "jpeg", "jpg"));
                //проверяем соответствует ли расширение файла расширению из иписка
                if (allowedExtensions.contains(extension))
                {

                        fileService.addAndsaveFle(file, id);

                    model.addAttribute("showOureFile",  "file saved successfully");

                    return "testSQL";

                }
                model.addAttribute("showOureFile", "Please select a file of one of the following types: pdf, png, doc, docx, jpg, jpeg.");
                return "testSQL";
            }
            model.addAttribute("showOureFile", "The size of attached file should not be greater than 5 Mb. Please select another file.");
            return "testSQL";
        }
        return  null;
    }



    @GetMapping("/getFile/{id}")
    public String creaneFile(Model model, @PathVariable("id") int id) {
        //загружаем файл на ПК
        fileService.downloadFileOnPC(id);


        return "redirect:/tickedLis";

    }


    @RequestMapping("/deleteFile/{id}")
    public String deleteFile (@PathVariable("id") int id, Principal principal, @RequestParam("id2") int id2,Model model){
       fileService.deleteFileUpdateTicked(fileService.getFileById(id), principal, id2);
      // fileService.deleteFile(fileService.getFileById(id));
       return "redirect:/tickedLis";
    }



    @RequestMapping("/testController")
    public String testController (@RequestParam("id2") int id2, Model model){
        model.addAttribute("id2", id2);
        model.addAttribute("ticked", tickedDAO.geTickedById(id2));
        return "infoAboutFile";
    }

    @GetMapping("/editForm/{id}")
    public String editForm(HttpServletRequest request, Principal principal, Model model, @PathVariable("id") int id, HttpSession session) {
       User user = userDAO.findByEmail(principal.getName());
        Ticked ticked = tickedDAO.geTickedById(id);

        model.addAttribute("state", ticked.state.getCat());
        model.addAttribute("loginOfCreator", ticked.getLoginOfcreater());
        model.addAttribute("rollOfCreater", ticked.getRollOfCreater());
        //объект для выбора правоприемника
        model.addAttribute("setAssignee", user);
        //объект содержащий логин текущего юзера для установления одобрителя
        model.addAttribute("editor_login", user.getLogin());
        model.addAttribute("request", request);
        model.addAttribute("form_ticket", ticked);
        model.addAttribute("ticket", managerDAO.onleExistsTickets());
        model.addAttribute("dateOfCreate",ticked.getCreate_date());
        model.addAttribute("loginOfcreater", ticked.getLoginOfcreater());
        if(ticked.getTickethistories()!=null) {
            model.addAttribute("setOfHistory", ticked.getTickethistories());
        }

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new Date());

            Set<Tickethistory> tickethistorySet = ticked.getTickethistories();
            session.setAttribute("testObject", tickethistorySet);


        if(ticked.getMyFile()!=null) {
            Set<MyFile> myFileSet = ticked.getMyFile();
            session.setAttribute("myFileSet", myFileSet);
        }
        if(ticked.getComments()!=null){
            Set<Comments> comments = ticked.getComments();
            session.setAttribute("comments", comments);
        }
        return "qwerty";
    }


    @PatchMapping("/updateTicked/{id}")
    public String updateTicked(HttpSession session, @ModelAttribute("form_ticket") Ticked ticked, @RequestParam("editor_login") String editor_login, BindingResult result, Principal principal, Model model, @PathVariable("id") int id
            , @RequestParam("cateorySelect")String cateorySelect /*@RequestParam(name="setOfHistory")Set<Integer> setOfHistoryId*/) {

        String cat = ticked.getState().getCat();
        List<String> listOfstateForAprover = Arrays.asList("APPROVED", "DECLINED", "CANCELED");
        if (listOfstateForAprover.contains(cat)&&ticked.getApprover().isEmpty()){
            ticked.setApprover(editor_login);
        }

        Set<Tickethistory> tickethistorySet = (Set)session.getAttribute("testObject");
        Set<MyFile> myFileSet = (Set)session.getAttribute("myFileSet");

        if(myFileSet!= null){
            ticked.setMyFile(myFileSet);
        }
        //0000000000000000000000000000000000000000000000000000000

        Set<Comments> comments = (Set)session.getAttribute("comments");
        ticked.setComments(comments);
        //0000000000000000000000000000000000000000000000000000000

        //if(){}
        Tickethistory tickethistory= historyDAO.createRecord(ticked);
        historyDAO.saveRecord(tickethistory);

        tickethistorySet.add(tickethistory);
        ticked.setTickethistories(tickethistorySet);

        tickedDAO.installChange(cateorySelect,ticked, id);



        return "redirect:/tickedLis";
    }


}


