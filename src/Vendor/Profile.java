package src.Vendor;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *  Profile implements the Profile that the Vendor profile extends.
 *  It contains all the attributes that are shared between the Customer and Vendor profiles
 *
 * @author Jordan Bronstetter
 * @date 03/18/2021
 *
 */
public abstract class Profile {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private int id;
    private String name = "";
    private String streetAddress;
    private String city;
    private StateAbbrs state;
    private String phoneNum;
    private double balance;
    private double lastPaidAmount;
    private LocalDate lastOrderDate;
    private static NumberFormat numberFormat = new DecimalFormat("#,##0.00");
    private static int phoneNumLength = 12;
    private static int maxChars = 20;

    public Profile(int id, String name, String streetAddress, String city, StateAbbrs state, String phoneNum,
                   double balance, double lastPaidAmount, LocalDate lastOrderDate) {
        setId(id);
        setName(name);
        setStreetAddress(streetAddress);
        setCity(city);
        setState(state);
        setPhoneNum(phoneNum);
        setBalance(balance);
        setLastPaidAmount(lastPaidAmount);
        setLastOrderDate(lastOrderDate);
    }

    public Profile(int id, String name, String streetAddress, String city,
                   StateAbbrs state, String phoneNum, LocalDate lastOrderDate) {
        this(id, name, streetAddress, city, state, phoneNum, 0, 0, lastOrderDate);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean setName(String name) {
        if (name.length() > maxChars) {
            return false;
        }
        this.name = name;
        return true;
    }

    public String getName() {
        return name;
    }

    public boolean setStreetAddress(String streetAddress) {
        if (streetAddress.length() > maxChars) {
            return false;
        }
        this.streetAddress = streetAddress;
        return true;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public boolean setCity(String city) {
        if (city.length() > maxChars) {
            return false;
        }
        this.city = city;
        return true;
    }

    public String getCity() {
        return city;
    }

    public void setState(StateAbbrs state) {
        this.state = state;
    }

    public StateAbbrs getState() {
        return state;
    }

    public boolean setPhoneNum(String phoneNum) {
        if (phoneNum.length() != phoneNumLength) {
            return false;
        }
        this.phoneNum = phoneNum;
        return true;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setLastPaidAmount(double lastPaidAmount) {
        this.lastPaidAmount = lastPaidAmount;
    }

    public double getLastPaidAmount() {
        return lastPaidAmount;
    }

    public void setLastOrderDate(LocalDate lastOrderDate) {
        this.lastOrderDate = lastOrderDate;
    }

    public LocalDate getLastOrderDate() {
        return lastOrderDate;
    }

    public int getMaxChars() {
        return maxChars;
    }

    public DateTimeFormatter formatter() {
        return formatter;
    }

    @Override
    public String toString() {
        String balanceText = numberFormat.format(balance);
        String lastPaidAmountText = numberFormat.format(lastPaidAmount);
        String date = lastOrderDate.format(formatter);

        return String.format("%s, %s, %s, %s, %s", name, phoneNum, balanceText, lastPaidAmountText, date);
    }
}
