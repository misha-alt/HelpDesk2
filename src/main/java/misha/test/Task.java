package misha.test;

public class Task {
    public static void main(String[] args) {
        String testString = "11";

        char[] testStringChar = testString.toCharArray();

        String [] one = {"one", "two", "tree", "four", "five", "six","seven", "eight", "nine", "ten"};
        String [] ten = {"eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};
        String [] tens= {"ten","twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "nine"};



        int number1 = Character.getNumericValue(testStringChar[0]);


        if(testString.length()==1){
            System.out.println(one[number1-1]);
        }

        if(testString.length()==2&&number1 !=0&& Character.getNumericValue(testStringChar[1])==0){
            System.out.println(tens[number1-1]);

        }

    if(testString.length()==2&&Character.getNumericValue(testStringChar[1])!=0) {

        if (testString.length() == 2 && number1 == 1) {
            int number2 = Character.getNumericValue(testStringChar[1]);
            String result = ten[number2 - 1];
            System.out.println(result);
        }
        if (testString.length() == 2 && number1 != 1) {
            int number2 = Character.getNumericValue(testStringChar[1]);
            System.out.println(tens[number1 - 1] + " " + one[number2 - 1]);
        }
    }




    }
}
