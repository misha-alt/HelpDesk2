package misha.test;

import misha.domain.Ticked;

import java.util.Comparator;

public class EnumComparatorTest  implements Comparator<ObjectTest> {

    @Override
    public int compare(ObjectTest o1, ObjectTest o2) {
        return o1.getSrt1().compareTo(o2.getSrt1());
    }
}

