package src.Vendor;

import java.text.NumberFormat;
import java.time.LocalDate;

public abstract class Profile {
    int id;
    String name = null;
    String streetAddress;
    String city;
    StateAbbrs state;
    String phoneNum;
    double balance;
    double lastPaidAmount;
    LocalDate lastOrderDate;
    NumberFormat numberFormat = NumberFormat.getInstance();

    int phoneNumLength = 12;
    int maxChars = 20;

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

    // fix when you understand it :(
    public double getLastPaidAmount() {
        return lastPaidAmount;
    }

    // fix when you understand it :(
    public void setLastOrderDate(LocalDate lastOrderDate) {
        this.lastOrderDate = lastOrderDate;
    }

    public LocalDate getLastOrderDate() {
        return lastOrderDate;
    }

    public int getMaxChars() {
        return maxChars;
    }

    @Override
    public String toString() {
        String balanceText = numberFormat.format(balance);
        String lastPaidAmountText = numberFormat.format(lastPaidAmount);

        return String.format("%s, %s, %s, %s, %s", name, phoneNum, balanceText, lastPaidAmountText, lastOrderDate);
    }
}
