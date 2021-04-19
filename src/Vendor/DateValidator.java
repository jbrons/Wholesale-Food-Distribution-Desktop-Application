package src.Vendor;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *  This class implements a Date Validator for validating user date inputs
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

    public boolean isFutureDate(LocalDate date) {
        LocalDate today = LocalDate.now();
        return today.isBefore(date);
    }

    public LocalDate getDate(String date) {
        if (isValid(date)) {
           return LocalDate.parse(date, dateFormat);
        }
        return null;
    }
}
