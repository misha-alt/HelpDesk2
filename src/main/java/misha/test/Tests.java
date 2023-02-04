package misha.test;

import com.google.common.collect.TreeMultiset;
import misha.domain.*;

import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.*;

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


       /* Student student1 = new Student("Dick",45);
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
        }*/

       /* String st = "NEW";
        State state = State.valueOf(st);

        System.out.println( state.getCat());*/


      /* for(State sateAll:State.values()){
           System.out.println(sateAll);
       }*/
        Ticked ticked = new Ticked();
        SomeEnum someEnum = SomeEnum.valueOf("HIGHT");
        System.out.println(someEnum.getCat());

       /* Set<Urgency> set = new TreeSet<>(Comparator.comparing(Urgency::toString));
        set.addAll(Arrays.asList(ticked.getUrgency())); // [4, 1, 2, 3]
        System.out.println(set); // [1, 2, 3, 4]
*/

    /*    ObjectTest objectTest2 = new ObjectTest("Two", "2");
        ObjectTest objectTest1 = new ObjectTest("One", "1");
        ObjectTest objectTest3 = new ObjectTest("Three", "3");
        Set<ObjectTest> obgCol = new TreeSet<>();

        obgCol.add(objectTest2);
        obgCol.add(objectTest1);
        obgCol.add(objectTest3);
        List<ObjectTest> arr = new ArrayList<>(obgCol);


        TreeSet<ObjectTest> multiset = new TreeSet<>(new EnumComparatorTest());
        multiset.addAll(arr);

        System.out.println( Arrays.toString(multiset.toArray()));*/

        TestReturn testReturn = new TestReturn();
        System.out.println(testReturn.somrMeth("three"));



    }

}