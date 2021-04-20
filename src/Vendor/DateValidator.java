package src.Vendor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *  DateValidator implements a Date Validator for validating user date inputs
 *  for the Vendor GUI
 *
 * @author Jordan Bronstetter
 * @date 03/18/2021
 *
 */
public class DateValidator {
    private DateTimeFormatter dateFormat;

    public DateValidator(DateTimeFormatter dateFormat) {
        this.dateFormat = dateFormat;
    }

    public boolean isValid(String date) {
        try {
            LocalDate.parse(date, dateFormat);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public static boolean isPastDate(LocalDate date) {
        LocalDate today = LocalDate.now();
        return date.isBefore(today);
    }

    public boolean isPastDate(String date) {
        return isPastDate(getDate(date));
    }

    public LocalDate getDate(String date) {
        if (isValid(date)) {
           return LocalDate.parse(date, dateFormat);
        }
        return null;
    }
}
