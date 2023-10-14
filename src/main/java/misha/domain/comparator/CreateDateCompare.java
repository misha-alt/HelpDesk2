package misha.domain.comparator;

import misha.domain.Ticked;
import sun.security.krb5.internal.Ticket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class CreateDateCompare implements Comparator<Ticked> {

    @Override
    public int compare(Ticked o1, Ticked o2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("dd/MM/yyyy");

        try {
            simpleDateFormat.parse(o1.getCreate_date());
            simpleDateFormat.parse(o2.getCreate_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  o1.getCreate_date().compareTo(o2.getCreate_date());
    }
}
