package misha.domain.comparator;

import misha.domain.Ticked;

import java.util.Comparator;

public class TicketIdComparator implements Comparator<Ticked> {

    @Override
    public int compare(Ticked o1, Ticked o2) {
        return o1.getId()-o2.getId();
    }

    @Override
    public Comparator<Ticked> reversed() {
        return Comparator.comparingInt(Ticked::getId).reversed();
    }
}
