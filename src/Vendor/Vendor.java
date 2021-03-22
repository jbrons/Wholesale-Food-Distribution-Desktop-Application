package src.Vendor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import src.Vendor.DateValidator;

/**
 *
 */
public class Vendor extends Profile {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    LocalDate seasonalDiscDate;
    static int id = 0;

    public Vendor(String fullName, String streetAddress, String city, StateAbbrs state, String phoneNum,
                  double balance, double lastPaidAmount, LocalDate lastOrderDate, LocalDate seasonalDiscDate) {
        super(++id, fullName, streetAddress, city, state, phoneNum, balance, lastPaidAmount, lastOrderDate);
        setSeasonalDiscDate(seasonalDiscDate);
    }

    public Vendor(String fullName, String streetAddress, String city,
                  StateAbbrs state, String phoneNum, LocalDate lastOrderDate, LocalDate seasonalDiscDate) {
        super(++id, fullName, streetAddress, city, state, phoneNum, lastOrderDate);
        setSeasonalDiscDate(seasonalDiscDate);
    }

    public void setSeasonalDiscDate(LocalDate seasonalDiscDate) {
        this.seasonalDiscDate = seasonalDiscDate;
    }

    public LocalDate getSeasonalDiscDate() {
        return seasonalDiscDate;
    }

   @Override
    public String toString() {
        return super.toString() + ", " + seasonalDiscDate.format(super.formatter());
    }
}
