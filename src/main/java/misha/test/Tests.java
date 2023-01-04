package misha.test;

import misha.domain.DateClass;
import misha.domain.State;
import misha.domain.Ticked;

import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static misha.domain.State.NEW;

public class Tests {
    public static void main(String[] args) {
       /* TestDate testDate = new TestDate();
        testDate.setVisible(true);*/

      /*  DateClass dateClass = new DateClass();
        System.out.println(dateClass.curentDate());
        */

  /* TestDate testDate = new TestDate();
        System.out.println(testDate.onleUsersWithComments().toString());*/


        Student student1 = new Student("Dick",45);
        Student student2 = new Student("Stan",28);
        Student student3 = new Student("Mike",32);
        Student student4 = new Student("Jon",23);


        List list = new ArrayList();
        list.add(student1);
        list.add(student2);
        list.add(student3);
        list.add(student4);


        Object[] myArray = list.toArray();
        Arrays.sort(myArray);
        for (Object myObject : myArray) {
            System.out.println(myObject);
        }

       /* String st = "NEW";
        State state = State.valueOf(st);

        System.out.println( state.getCat());*/


       for(State sateAll:State.values()){
           System.out.println(sateAll);
       }


    }




}
