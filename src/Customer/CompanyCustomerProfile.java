package src.Customer;
/**
 *
 *
 */


public class CompanyCustomerProfile implements CustomerProfile {
    /**
     * Static ID for unique per each profile
     */
    public static int ID = 100000;
    /**
     * State list in us
     */

    public static String[] STATES = new String[] {
            "AL",
            "AK",
            "AS",
            "AZ",
            "AR",
            "CA",
            "CO",
            "CT",
            "DE",
            "DC",
            "FL",
            "GA",
            "GU",
            "HI",
            "ID",
            "IL",
            "IN",
            "IA",
            "KS",
            "KY",
            "LA",
            "ME",
            "MD",
            "MA",
            "MI",
            "MN",
            "MS",
            "MO",
            "MT",
            "NE",
            "NV",
            "NH",
            "NJ",
            "NM",
            "NY",
            "NC",
            "ND",
            "MP",
            "OH",
            "OK",
            "OR",
            "PA",
            "PR",
            "RI",
            "SC",
            "SD",
            "TN",
            "TX",
            "UT",
            "VT",
            "VA",
            "VI",
            "WA",
            "WV",
            "WI",
            "WY" };

    /**Properties of profile
     *
     */
    private int customerID;
    private String customerName;
    private String streetAddress;
    private String city;
    private String state;
    private String phone;
    private float balance;
    private float lastPaidAmount;
    private String lastOrderDate;

    /**Constructor
     *
     * @param customerName
     * @param streetAddress
     * @param city
     * @param state
     * @param phone
     * @param balance
     * @param lastPaidAmount
     * @param lastOrderDate
     */
    public CompanyCustomerProfile(String customerName, String streetAddress, String city, String state, String phone, float balance, float lastPaidAmount, String lastOrderDate) {
        this.customerID = ++ID;
        this.customerName = customerName;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.phone = phone;
        this.balance = balance;
        this.lastPaidAmount = lastPaidAmount;
        this.lastOrderDate = lastOrderDate;
    }

    /** Get methods
     *
     * @return
     */
    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPhone() {
        return phone;
    }

    public float getBalance() {
        return balance;
    }

    public float getLastPaidAmount() {
        return lastPaidAmount;
    }

    public String getLastOrderDate() {
        return lastOrderDate;
    }

    /**Set Methods
     *
     * @param customerName
     */
    public void setCustomerName(String customerName) {

        this.customerName = customerName;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setLastPaidAmount(float lastPaidAmount) {
        this.lastPaidAmount = lastPaidAmount;
    }

    public void setLastOrderDate(String lastOrderDate) {
        this.lastOrderDate = lastOrderDate;
    }

    /**Make string for listview
     *
     * @return
     */
    @Override
    public String toString() {
        return "" + customerName +
                ", " + phone +
                ", " + balance +
                ", " + lastPaidAmount;
    }

}
