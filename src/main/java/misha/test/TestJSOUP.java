package misha.test;


import java.util.*;

import static java.lang.Character.isDigit;

public class TestJSOUP {
    int mony;
    public static void main(String[] args) {
         int arr[] = {1,3,2,4,1};
        List<Integer> list = new ArrayList<>();
        TestJSOUP testJSOUP = new TestJSOUP();
         for(int i=0;i<arr.length;i++){
             for(int j=1;j<arr.length;j++){
                 if(arr[i]+arr[j]==7){
                     testJSOUP.mony=arr[i]+arr[j];
                     list.add(arr[i]);
                     list.add(arr[j]);
                     System.out.println(testJSOUP.mony+", "+arr[i]+", "+arr[j]);

                 }
             }
         }
        System.out.println(testJSOUP.mony);
         int f = list.size();
        System.out.println(list.size());
        System.out.println("-------------------------------------");
        charMath();
    }
public static void charMath(){
        String s= "3+2*4-5";//6
int sum;
    Stack<Integer>integerStack = new Stack<>();
    Stack<Character>characterStack = new Stack<>();


   char[] ch = s.toCharArray();
   for(Character character:ch){
       if (Character.isDigit(character)){
           System.out.println(character);
           int chDigit = Character.getNumericValue(character);
            integerStack.push(chDigit);

       }

   }
    for(Character character:ch){
        if (!Character.isDigit(character)){
            System.out.println(character);
            characterStack.push(character);

        }

    }


    if (characterStack.peek()=='-'){
        characterStack.pop();
        int q =integerStack.peek();
        integerStack.pop();
        int w =integerStack.peek();
        sum=q-w;
    }

    if (characterStack.peek()=='*'){
        characterStack.pop();
        int q =integerStack.peek();
        integerStack.pop();
        int w =integerStack.peek();
        sum=q-w;
    }

   /* String rpn = toRPN(s);
    System.out.println("Обратная польская запись: " + rpn);

    // Вычисление результата
    int result = evaluateRPN(rpn);
    System.out.println("Результат: " + result);
*/
}


}
