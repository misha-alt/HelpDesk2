package misha.domain;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
@Service
public class DateClass {



   /* public String  curentDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");

        return simpleDateFormat.format(date);
    }
*/






        public static LocalDate madeComment() {
            return LocalDate.now();
        }
}
