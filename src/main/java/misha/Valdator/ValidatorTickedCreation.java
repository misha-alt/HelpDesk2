package misha.Valdator;

import misha.dao.TickedDAO;
import misha.dao.UserDAO;
import misha.domain.Ticked;
import misha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
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
        //поле имени билета не должно быть пустым
        ValidationUtils.rejectIfEmpty(errors, "name", "errorCodeNameOfTickedNotEmpty");
        Ticked ticked = (Ticked) o;
        //User user = userDAO.getByLogin( ticked.getLoginOfcreater()).get(0);

        char[] charArray = ticked.getName().toCharArray();

        //устанавливаем ограничение размера имени билета
        if (charArray.length > 500) {
            errors.rejectValue("name", "errorCodeNameOfTickedLenght");
        }


    }
}
