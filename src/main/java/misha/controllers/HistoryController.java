package misha.controllers;

import misha.dao.FeedbackDAO;
import misha.dao.HistoryDAO;
import misha.dao.TickedDAO;
import misha.domain.FeedBack;
import misha.domain.MyFile;
import misha.domain.Ticked;
import misha.domain.Tickethistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Transactional
public class HistoryController {


    private HistoryDAO historyDAO;
    private TickedDAO tickedDAO;
    private FeedbackDAO feedbackDAO;

    @Autowired
    public HistoryController(HistoryDAO historyDAO, TickedDAO tickedDAO, FeedbackDAO feedbackDAO) {
        this.historyDAO = historyDAO;
        this.tickedDAO = tickedDAO;
        this.feedbackDAO = feedbackDAO;

    }

    @RequestMapping("/only/{id}")
    public String viewHistory(HttpSession session, Model model, @PathVariable("id")int id){


        Set<Tickethistory>  set= tickedDAO.geTickedById(1).getTickethistories();
        List<Tickethistory> list = new ArrayList<>(set);
        list.get(0);

        Set<MyFile>set1 = list.get(0).getMyFiles();
        List<MyFile> list1 = new ArrayList<>(set1);

        //list1.get(0).getFile_name();

     model.addAttribute("history", tickedDAO.geTickedById(id).getTickethistories());
        model.addAttribute("oneHistory",  list1);
     model.addAttribute("fromTicked", historyDAO.grtFortest());

        return "onlyForTest";
    }
    @RequestMapping("/rate/{id}")
    public String rateContr(Model model, @PathVariable("id") int id){
      if(tickedDAO.geTickedById(id).getFeedBacks()!=null&& !tickedDAO.geTickedById(id).getFeedBacks().isEmpty()) {
          List<FeedBack> list = new ArrayList<>(tickedDAO.geTickedById(id).getFeedBacks());
          model.addAttribute("existRate", list.get(0).getRate());
      }else {
          model.addAttribute("existRate", "make you rate");
      }
        model.addAttribute("rate", new FeedBack());
       model.addAttribute("ticket_id",id);

        return "rate";
    }
    @RequestMapping("/saveRate/{id}")
    public String saveRate( Model model,@ModelAttribute("rate") FeedBack feedBack, @PathVariable("id")int id){

       DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
       String currentDate = dateFormat.format(new Date());
       feedBack.setDate_rate(currentDate);

       Ticked ticked =  tickedDAO.geTickedById(id);
       ticked.getFeedBacks().add(feedBack);

       feedbackDAO.saveRate(feedBack);
       List<FeedBack> list = new ArrayList<>(tickedDAO.geTickedById(id).getFeedBacks());
       model.addAttribute("checkRate",list.get(0).getRate());

       return "tempered";
    }
}
