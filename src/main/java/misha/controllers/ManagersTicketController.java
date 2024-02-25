package misha.controllers;


import misha.dao.ManagerDAO;
import misha.dao.TickedDAO;
import misha.dao.UserDAO;
import misha.domain.State;
import misha.domain.Ticked;
import misha.domain.User;
import misha.service.ManagerService;
import misha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Controller
@Transactional
public class ManagersTicketController {

    private ManagerDAO managerDAO;
    private UserDAO userDAO;
    private TickedDAO tickedDAO;
    @Autowired
    public ManagersTicketController(ManagerDAO managerDAO, UserDAO userDAO, TickedDAO tickedDAO) {
        this.managerDAO = managerDAO;
        this.userDAO = userDAO;
        this.tickedDAO = tickedDAO;
    }

    @RequestMapping("/ticketListOfManager")
    public String ticketListOfManager(Principal principal, Model model,@RequestParam(value = "var", defaultValue = "id") String var){
        User user = userDAO.findByEmail(principal.getName());

        List list = tickedDAO.getTickedNew();
        model.addAttribute("newTicked", tickedDAO.methodForSort(var, list, principal));

        List<Ticked> list1= managerDAO.getAllTickedInProgress();
        model.addAttribute("inPogressTicked", tickedDAO.methodForSort(var, list1, principal));

        List list2 = managerDAO.getAllTickedDone();
        model.addAttribute("doneTicked", tickedDAO.methodForSort(var, list2, principal));
        return "ticketListOfManager";
    }

    @RequestMapping ("/done/{id}")
    public String doneController (Principal principal, Model model, @RequestParam("managersSolution") String managersSolution, @PathVariable("id") int id ){

       Ticked ticked= tickedDAO.geTickedById(id);

       ticked.setState(State.valueOf(managersSolution));
       tickedDAO.updateTcked(ticked);

        return "redirect:/ticketListOfManager";
    }

}
