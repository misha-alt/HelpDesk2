package misha.controllers;

import misha.dao.FeedbackDAO;
import misha.dao.HistoryDAO;
import misha.dao.TickedDAO;
import misha.dao.UserDAO;
import misha.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Transactional
public class HistoryController {


    private HistoryDAO historyDAO;
    private TickedDAO tickedDAO;
    private FeedbackDAO feedbackDAO;
    private UserDAO userDAO;

    @Autowired
    public HistoryController(HistoryDAO historyDAO, TickedDAO tickedDAO, FeedbackDAO feedbackDAO, UserDAO userDAO) {
        this.historyDAO = historyDAO;
        this.tickedDAO = tickedDAO;
        this.feedbackDAO = feedbackDAO;
        this.userDAO = userDAO;
    }
    //показывает страницу истории билета
    @RequestMapping("/only/{id}")
    public String viewHistory(/*HttpSession session,*/ Model model, @PathVariable("id")int id){

        Ticked ticked = tickedDAO.geTickedById(id);
        Set<Tickethistory>  set= ticked.getTickethistories();//достаем билет из базы и берем его сет историй
        List<Tickethistory> list = new ArrayList<>(set);
        list.get(0);

        Set<MyFile>set1 = list.get(0).getMyFiles();
        List<MyFile> list1 = new ArrayList<>(set1);

        //list1.get(0).getFile_name();
        model.addAttribute("id", id);
        model.addAttribute("history", list);//истрия билета
        if(ticked.getMyFile()!=null&&!ticked.getMyFile().isEmpty()) {
            model.addAttribute("tickedFile", ticked.getMyFile());
        }


        return "onlyForTest";
    }

    //показывает страницу оценки
    @RequestMapping("/rate/{id}")
    public String rateContr(HttpSession session,Principal principal, Model model, @PathVariable("id") int id, RedirectAttributes redirectAttributes){
        User user = userDAO.findByEmail(principal.getName());
        Ticked ticked = tickedDAO.geTickedById(id);
        //если оценка уже проставлена показываем её, если нет показываем меню выбора оценки
        if(ticked.getFeedBacks()!=null&&!ticked.getFeedBacks().isEmpty()) {
            List list = new ArrayList(ticked.getFeedBacks());

            FeedBack feedBack = (FeedBack) list.get(0);
          model.addAttribute("existRate", feedBack.getRate()+" "+ ticked.getId());

      }else {
          model.addAttribute("existRate", "make you rate");
      }
        model.addAttribute("rate", new FeedBack());
        model.addAttribute("id", id);
       // session.setAttribute("ticket_id",ticked.getId());

        return "rate";
    }
    @RequestMapping("/saveRate/{id}")
    public String saveRate(HttpSession session,Principal principal, Model model, @ModelAttribute("rate") FeedBack feedBack, @PathVariable("id")int id){

       DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
       String currentDate = dateFormat.format(new Date());
       feedBack.setDate_rate(currentDate);

       feedbackDAO.saveRate(feedBack);

       //сохраняем оценку в у билета в сете
//        int id = (Integer) session.getAttribute("ticket_id");
        Ticked ticked =  tickedDAO.geTickedById(id);
        ticked.getFeedBacks().add(feedBack);
        tickedDAO.updateTcked(ticked);

       //сохраняем оценк у пользователя в сете
       /*User user =userDAO.findByEmail(principal.getName());
       user.getFeedBacks().add(feedBack);
       userDAO.updateUser(user);*/

        model.addAttribute("id", id);
        session.setAttribute("id", id);

       return "redirect:/rate/"+id;
       /* "redirect:/tickedLis"*/

    }

    @RequestMapping("/wer")
    public String wer(HttpSession session, Model model, Principal principal){


      int id = (Integer) session.getAttribute("id");
        Ticked ticked1 = tickedDAO.geTickedById(id);
        model.addAttribute("id", ticked1.getId());
        List<FeedBack> list = new ArrayList<>(ticked1.getFeedBacks());

            model.addAttribute("rate", ticked1.getFeedBacks());





      return "tempered";

    }
}
