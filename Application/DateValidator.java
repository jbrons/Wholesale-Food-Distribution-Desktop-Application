package Application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator {
    private DateTimeFormatter dateFormat;

    public DateValidator(DateTimeFormatter dateFormat) {
        this.dateFormat = dateFormat;
    }

   // @Override
    public boolean isValid(String date) {
        try {
            LocalDate.parse(date, dateFormat);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public LocalDate getDate(String date) {
        if (isValid(date)) {
           return LocalDate.parse(date, dateFormat);
        }
        return null;
    }
}
