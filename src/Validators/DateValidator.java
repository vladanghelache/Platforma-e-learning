package Validators;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator {

    public boolean validateDate(String date){
        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException e){
            return false;
        }
        return true;
    }

}
