package misha.domain;

import java.util.Comparator;

public class EnumComparator implements Comparator<Ticked> {
    @Override
    public int compare(Ticked o1, Ticked o2) {
        return o1.getUrgency().getCatt().compareTo(o2.getUrgency().getCatt());
    }
}