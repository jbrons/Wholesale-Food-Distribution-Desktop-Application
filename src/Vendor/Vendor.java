package src.Vendor;
import java.time.LocalDate;

/**
 *  Vendor implements the Vendor profile for the owner
 *  and purchaser users to create, update, and delete Vendors
 *
 * @author Jordan Bronstetter
 * @date 03/18/2021
 *
 */
public class Vendor extends Profile {
    private LocalDate seasonalDiscDate;
    private static int id = 0;
    private static int maxIDSize = 999999;

    public Vendor(String fullName, String streetAddress, String city, StateAbbrs state, String phoneNum,
                  double balance, double lastPaidAmount, LocalDate lastOrderDate, LocalDate seasonalDiscDate) {
        super(setId(), fullName, streetAddress, city, state, phoneNum, balance, lastPaidAmount, lastOrderDate);
        setSeasonalDiscDate(seasonalDiscDate);
    }

    public Vendor(String fullName, String streetAddress, String city,
                  StateAbbrs state, String phoneNum, LocalDate lastOrderDate, LocalDate seasonalDiscDate) {
        super(setId(), fullName, streetAddress, city, state, phoneNum, lastOrderDate);
        setSeasonalDiscDate(seasonalDiscDate);

    }

    private static int setId() {
        if (id < maxIDSize) {
            ++id;
        } else {
            findNewId();
        }
        return id;
    }

    private static int findNewId() {
        id = 1;
        while (VendorDatabase.getInstance().searchVendorDatabase(id) != -1) {
            ++id;
        }
        if (id < maxIDSize) {
            return id;
        } else {
            return -1;
        }
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
