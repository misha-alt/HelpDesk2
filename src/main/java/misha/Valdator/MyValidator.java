package misha.Valdator;


import misha.domain.User;
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
        ValidationUtils.rejectIfEmpty(errors, "name", "errorCodeFrsrName");
        User user = (User) o;
        char[] charArray = user.getFirst_name().toCharArray();
        if (charArray.length < 2) {
            errors.rejectValue("first_name","errorCodeFrsrName" );
        } else if (charArray.length > 30) {
            errors.rejectValue("first_name", "errorCodeFirstName2");
        }
    }
}
