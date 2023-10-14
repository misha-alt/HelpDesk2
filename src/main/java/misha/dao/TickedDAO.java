package misha.dao;

import misha.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface TickedDAO {
    Ticked geTickedById(int id);
    List<Ticked> getTickedByUrgency(Urgency urgency);
    List<Ticked> getTicketByStatus(String state);
    List<Ticked> listOfTickedCurrentUser(String login);
    List<Ticked> managerAsAppruverAndStateDeclin(String approver);
    void creationTiket (Ticked ticked, String cateorySelect, String MyState, String UrgencyState,
                        String nameOfAssignee, String nameOfApprover,String engineerSuccessorr, Principal principal);
    List<Ticked> methodForSort(String var, Principal principal);
    List<Ticked> sortedlistOfTicked(Principal principal);
    List<Ticked> sortedListById (Principal principal);
    List<Ticked> sortedByDate(Principal principal);
    List<Ticked> filteredListByCriteria(String var);
    List<Ticked> sortedByAlphabet(Principal principal);
    List<Ticked> sortedByState(Principal principal);
    void updateTcked(Ticked ticked);
    void deleteTicket(int id);
    void installChange(String cateorySelect, Ticked ticked, int id);
    void editDrafTicked(Ticked ticked, String engineerSuccessorr);
    List<Ticked> getByName(String name);
    void saveTicked(Ticked ticked);
    List<Ticked> sortedByCreationDate(Principal principal);
    void saveOrUpdate(Ticked ticked);
    void saveMergeTicked (Ticked ticked);

    List<Ticked> getAllTicked();




}
