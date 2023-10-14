package misha.domain.comparator;

import misha.domain.Ticked;

import java.util.Comparator;

public class AlphabetComparator implements Comparator<Ticked> {
    @Override
    public int compare(Ticked str1, Ticked str2) {
        return str1.getName().compareTo(str2.getName());
    }
}
