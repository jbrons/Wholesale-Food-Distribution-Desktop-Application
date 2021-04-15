package src.Customer;

/**
 * Interface of Profile
 *

 */


public interface CustomerProfile {

    int getCustomerID();

    String getCustomerName();

    String getStreetAddress();

    String getCity();

    String getState();

    String getPhone();

    float getBalance();

    float getLastPaidAmount();

    String getLastOrderDate();

    void setCustomerName(String customerName);

    void setStreetAddress(String streetAddress);

    void setCity(String city);

    void setState(String state);

    void setPhone(String phone);

    void setBalance(float balance);

    void setLastPaidAmount(float lastPaidAmount);

    void setLastOrderDate(String lastOrderDate);
}
