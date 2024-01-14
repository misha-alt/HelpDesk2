package misha.service;


import misha.domain.User;
import org.springframework.stereotype.Service;

@Service
public class FormValidationMeth {

    public boolean validMeth (User user){

        char[] charArray= user.getPassword().getPassword().toCharArray();
        char[] charArray2 = user.getFirst_name().toCharArray();
        char[] charArray3 = user.getLast_name().toCharArray();
        char[] charArray4 = user.getLogin().toCharArray();


        if (charArray.length>30||charArray.length<=1||
                charArray2.length>30||charArray2.length<=1||
                charArray3.length>30||charArray3.length<=1||
                charArray4.length>30||charArray4.length<=1){

            return false;
        }
        return true;
    }
    public boolean validemail(User user){

        char[] charArray= user.getEmail().toCharArray();
        for(int i =0;i<charArray.length;i++){
            if (charArray[i]=='@')
                return true;
        }
        return  false;
    }

    public boolean validSpecialCharacters(User user){

        char[] charArray1= user.getPassword().getPassword().toCharArray();
        char[] charArray2 = user.getFirst_name().toCharArray();
        char[] charArray3 = user.getLast_name().toCharArray();
        char[] charArray4 = user.getLogin().toCharArray();

        String  sample =  "~.\"(),:;<>@[]!#$%&'*+-/=?^_`{|}";
        char[] charSample = sample.toCharArray();

        return true;
    }
}
