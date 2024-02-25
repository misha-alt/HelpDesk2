package misha.domain.comparator;

import misha.domain.Ticked;
import misha.domain.Tickethistory;
import sun.nio.cs.ext.TIS_620;

import java.util.Comparator;

public class SortedHistoryById implements Comparator<Tickethistory> {
    @Override
    public int compare(Tickethistory o1, Tickethistory o2) {
        return o1.getId()-o2.getId();
    }
}
