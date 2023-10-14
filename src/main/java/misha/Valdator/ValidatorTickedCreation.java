package misha.Valdator;

import misha.domain.Ticked;
import misha.domain.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ValidatorTickedCreation implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Ticked.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "errorCodeNameOfTickedNotEmpty");
        Ticked ticked = (Ticked) o;
        char[] charArray = ticked.getName().toCharArray();


        //устанавливаем ограничение размера имени билета
        if (charArray.length > 500) {
            errors.rejectValue("name", "errorCodeNameOfTickedLenght");
        }

    }
}
