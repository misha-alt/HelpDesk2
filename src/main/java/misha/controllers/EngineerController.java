package misha.controllers;


import misha.dao.*;
import misha.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//этот контроллер возвращает страницу инженера
@Controller
@Transactional
public class EngineerController {

    private ManagerDAO managerDAO;
    private UserDAO userDAO;
    private TickedDAO tickedDAO;
    private CreateCommDAO createCommDAO;
    private EngineerDAO engineerDAO;
    private HistoryDAO historyDAO;
    @Autowired
    public EngineerController(ManagerDAO managerDAO, UserDAO userDAO, TickedDAO tickedDAO, CreateCommDAO createCommDAO, EngineerDAO engineerDAO, HistoryDAO historyDAO) {
        this.managerDAO = managerDAO;
        this.userDAO = userDAO;
        this.tickedDAO = tickedDAO;
        this.createCommDAO = createCommDAO;
        this.engineerDAO = engineerDAO;
        this.historyDAO = historyDAO;
    }

    @RequestMapping("/engineer")
    public String viewEngineer(Principal principal, Model model){
        User user = userDAO.findByEmail(principal.getName());
        model.addAttribute("EngineerName",user.getFirst_name());
        model.addAttribute("listOfTicked", engineerDAO
                .ticketsCreatedByAllEmployeesAndManagersInStatusApproved(user.getLogin()));


        return "engineer";
    }

    @RequestMapping("/tiskedListOfEngeneer")
    public String tiskedListOfEngeneer(Principal principal, Model model,@RequestParam(value = "var", defaultValue = "id") String var){
        User user = userDAO.findByEmail(principal.getName());


       List list = tickedDAO.getTickedNew();
        model.addAttribute("newTicked", tickedDAO.methodForSort(var, list, principal));

       List<Ticked> list1= tickedDAO.getTickedInProgress(userDAO.findByEmail(principal.getName()).getLogin());
        model.addAttribute("inPogressTicked", tickedDAO.methodForSort(var, list1, principal));

        List list2 = tickedDAO.getUserTickedDone();
        model.addAttribute("doneTicked", tickedDAO.methodForSort(var, list2, principal));
        return "tickedListOfEngineer";
    }


        @RequestMapping("/appoint/{id}")
    public String assignControier(Principal principal, @PathVariable("id") int id){
        Ticked ticked = tickedDAO.geTickedById(id);
            User user = userDAO.findByEmail(principal.getName());
        ticked.setAssignee(user.getLogin());
        ticked.setState(State.INPROGRESS);



            //создам объект история билета, заполняем его и сохрняем
            Tickethistory tickethistory= historyDAO.createRecord(ticked);

            ticked.getTickethistories().add(tickethistory);
            historyDAO.saveRecord(tickethistory);




            tickedDAO.updateTcked(ticked);



          /*  //создам объект история билета, заполняем его и сохрняем
            Tickethistory tickethistory= historyDAO.createRecord(ticked);
            historyDAO.saveRecord(tickethistory);


            //достаем историю билета из БД, добавляем ее в сет история билета и обновляем билет
            Tickethistory tickethistory1 = historyDAO.getById(tickethistory.getId());
            Ticked ticked1 = tickedDAO.geTickedById(ticked.getId());
            Set<Tickethistory> set = new HashSet<>();
            set.add(tickethistory1);
            ticked1.setTickethistories(set);
            tickedDAO.updateTcked(ticked1);
*/

        return "redirect:/tiskedListOfEngeneer";

    }

    @RequestMapping("/approving/{id}")
    public String approvingTicket(Principal principal, @PathVariable("id") int id){
        Ticked ticked = tickedDAO.geTickedById(id);
        ticked.setState(State.APPROVED);

        //создам объект история билета, заполняем его и сохрняем
        Tickethistory tickethistory= historyDAO.createRecord(ticked);

        ticked.getTickethistories().add(tickethistory);
        historyDAO.saveRecord(tickethistory);




        tickedDAO.updateTcked(ticked);


        return "redirect:/tiskedListOfEngeneer";
    }

}