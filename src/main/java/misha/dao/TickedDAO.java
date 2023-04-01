package misha.dao;

import misha.domain.Ticked;
import misha.domain.Urgency;

import java.security.Principal;
import java.util.List;

public interface TickedDAO {
    Ticked geTickedById(int id);
    List<Ticked> getTickedByUrgency(Urgency urgency);
    List<Ticked> getTicketByStatus(String state);
    List<Ticked> listOfTickedCurrentUser(String login);
    List<Ticked> managerAsAppruverAndStateDeclin(String approver);
    void creationTiket (Ticked ticked, String MyState, String UrgencyState,
                        String nameOfAssignee, String nameOfApprover, Principal principal);
    List<Ticked> methodForSort(String var, Principal principal);
    List<Ticked> sortedlistOfTicked(Principal principal);
    List<Ticked> sortedListById (Principal principal);
    List<Ticked> sortedByDate(Principal principal);
    List<Ticked> filteredListByCriteria(Object someCreteria);

}
