package misha.test;

public class Student {

    public static void main(String[] args) {
        int[][] testArray = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};

        for (int i = 0; i < testArray.length; i++) {

            for (int j = 0; j < testArray[i].length; j++) {
                if (testArray[i][j] ==4) {
                    if (j+1 <= testArray[i].length - 1) {

                        System.out.print(testArray[i][j+1]);
                    } else {
                        System.out.println("Index out of bound exeaption");
                    }

                }

            }

        }

    }
}



