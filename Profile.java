/** Interface of Profile
 *
 */


/**
 *
 * @Author : Joyshree Chowdhury
 *
 *
 */
public interface Profile {
    /**
     * Auto generated customer id ( 6 numerical characters )
     *
     */
    int getCustomerID();

    /**
     *
     * Full  name : max 20 characters
     */
    String getCustomerName();

    /**
     *
     * street address : max 20 characters
     */

    String getStreetAddress();
    /**
     *
     * City : max 20 characters
     */

    String getCity();

    /**
     *
     * State: 2 characters : select from a list
     */
    String getState();

    String getPhone();

    /**
     *
     * Balance and last paid amount has default value 0 and it is
     * not a required field for profile creation.
     */
    float getBalance();

    float getLastPaidAmount();

    /**
     *
     * Lat order date has no values set
     * during profile creation.
     */

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
