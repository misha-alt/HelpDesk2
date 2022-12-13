package misha.test;

import misha.domain.DateClass;

import java.time.LocalDate;

public class Tests {
    public static void main(String[] args) {
       /* TestDate testDate = new TestDate();
        testDate.setVisible(true);*/

      /*  DateClass dateClass = new DateClass();
        System.out.println(dateClass.curentDate());
        */

   TestDate testDate = new TestDate();
        System.out.println(testDate.onleUsersWithComments().toString());



    }

}
