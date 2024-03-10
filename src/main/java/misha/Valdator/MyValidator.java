package misha.Valdator;


import misha.dao.UserDAO;
import misha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Set;

public class MyValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "first_name", "errorCodeEmptyFild");
        ValidationUtils.rejectIfEmpty(errors, "last_name", "errorCodeEmptyFild");
        ValidationUtils.rejectIfEmpty(errors, "login", "errorCodeEmptyFild");
        ValidationUtils.rejectIfEmpty(errors, "password", "errorCodeEmptyFild");
        ValidationUtils.rejectIfEmpty(errors, "email", "errorCodeEmptyFild");
        User user = (User) o;
        char[] charArray = user.getFirst_name().toCharArray();
        char[] charArray2 = user.getLast_name().toCharArray();
        char[] charArray3 = user.getEmail().toCharArray();

        String strPass = user.getPassword();
        String str = new String(charArray3);


        //проверка Email на наличие '@'
        int e = 0;
        for (int i = 0; i < charArray3.length; i++) {
            if (charArray3[i] == '@') {
                e = 1;
            }
        }

        boolean hasUpperCase = false;
        boolean hasDigits = false;
        boolean hasUnicode = false;

        //проверк логина на уникальность



        //проверка имени на размер
        if (charArray.length < 2) {
            errors.rejectValue("first_name", "errorCodeFrsrName");
        } else if (charArray.length > 30) {
            errors.rejectValue("first_name", "errorCodeFirstName2");
        }

        //проверка пароля на размер
        else if (strPass.toCharArray().length < 6||strPass.toCharArray().length > 20) {
            errors.rejectValue("password", "errorCodePassword");
        }
        //проверка фамилии на размео
        else if (charArray2.length < 2) {
            errors.rejectValue("last_name", "errorCodeLastName");
        } else if (charArray2.length > 30) {
            errors.rejectValue("last_name", "errorCodeLastName2");
        }

        //проверка Email на первый и последний символ равный '@'
        else if (charArray3[0] == '@' || charArray3.length - 1 == '@') {
            errors.rejectValue("email", "errorCodeEmail2");
        } else if (e != 1) {
            errors.rejectValue("email", "errorCodeEmail");
        } else if (str.startsWith(".") || str.endsWith(".")) {
            errors.rejectValue("email", "errorCodeEmail3");
        }
        //проверка пароля на буквы, заглавные, маленькие, на наличчие специальных символов
        if (!strPass.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[~.\"(),:;<>@\\[\\]!#$%&'*+-/=?^_`{|}])[a-zA-Z0-9~.\"(),:;<>@\\[\\]!#$%&'*+-/=?^_`{|}]+$")) {
            errors.rejectValue("password", "errorCodePasswordUnicode");

        }

    }
}



/*
"^[a-zA-Z0-9]+$"*/
/*"^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]+$"*/