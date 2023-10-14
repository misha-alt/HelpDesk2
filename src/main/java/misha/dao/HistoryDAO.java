package misha.dao;

import misha.domain.Ticked;
import misha.domain.Tickethistory;

import java.util.List;

public interface HistoryDAO {
     Tickethistory createRecord (Ticked ticked);
    void updateRecord(Tickethistory ticketHistory);
    void saveRecord(Tickethistory ticketHistory);
    Tickethistory showeHistory(int id);
    Tickethistory grtFortest();
    Tickethistory getById(int id);
}
