package misha.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JUnitTest {


    public static void main(String[] args) {
        ObjectTest objectTest = new ObjectTest();
        objectTest.Met();
        Set<ObjectTest> set = new HashSet<>();
        set.add(objectTest);
        set.forEach((element) -> {
            System.out.println(element);
        });
        set.remove(objectTest);
        set.forEach((element) -> {
            System.out.println(element);
        });
    }



}
