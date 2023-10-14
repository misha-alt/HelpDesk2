package misha.domain.comparator;

import misha.domain.Ticked;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class DataComp implements Comparator<Ticked>  {
    @Override
    public int compare(Ticked o1, Ticked o2) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("dd/MM/yyyy");

        try {
            simpleDateFormat.parse(o1.getDesireddate());
            simpleDateFormat.parse(o2.getDesireddate());
        } catch (ParseException e) {
            e.printStackTrace();
        }



        return  o1.getDesireddate().compareTo(o2.getDesireddate());
    }
}
