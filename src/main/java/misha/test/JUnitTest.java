package misha.test;

import java.util.*;

public class JUnitTest {


    public static void main(String[] args) {
        /*ObjectTest objectTest = new ObjectTest();
        objectTest.Met();
        Set<ObjectTest> set = new HashSet<>();
        set.add(objectTest);
        set.forEach((element) -> {
            System.out.println(element);
        });
        set.remove(objectTest);
        set.forEach((element) -> {
            System.out.println(element);
        });*/
        int[]mass = {2,1,6,3,5};
        massBabble(mass);

    }




    public static void  massBabble(int []mass){

        int buf;
        boolean isSorted = false;
                while(!isSorted) {
                    isSorted = true;
                    for (int i = 0; i < mass.length-1; i++) {
                        if (mass[i] > mass[i + 1]) {
                            isSorted = false;
                            buf = mass[i];
                            mass[i] = mass[i + 1];
                            mass[i + 1] = buf;

                        }
                    }

                }
        for (int i = 0; i < mass.length; i++) {
            System.out.println(mass[i]);
        }

    }

}
