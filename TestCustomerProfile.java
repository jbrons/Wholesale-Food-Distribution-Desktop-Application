import javax.swing.*;
import java.util.ArrayList;

/**
 *Test class for customer profile.
 *
 * Adding all the functionalities for customer profile.
 *
 * @Author : Joyshree Chowdhury
 *
 *
 */
public class TestCustomerProfile {
    /** GUI Components
     *
     */
    private JPanel panel1;
    private JButton editProfileButton;
    private JButton deleteProfileButton;
    private JButton exitButton;
    private JButton addNewProfileButton;
    private JPanel Buttons;
    private JPanel MainPanel;
    private JList<String> list1;
    private JTextField txtSearchField;
    private JButton searchButton;
    private JButton viewAllButton;

    /**Constructor
     *
     */
    TestCustomerProfile() {
        /** Add new profile button clicked
         *
         * add action listener
         */
        addNewProfileButton.addActionListener(e -> {
            /**show DetailProfile with null parameter
             *
             */
            JFrame frame = new JFrame("DetailProfile");
            frame.setContentPane(new CustomerProfileDetail(this).panel1);
            frame.setSize(500, 300);
            frame.setVisible(true);
            frame.pack();
        });

        /** Edit Profile button clicked
         *
         * add action listener.
         */
        editProfileButton.addActionListener(e -> {
            /** Check owner user select the item(customer profiles)
             *  in the list
             *
             */
            if (list1.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Please select profile to edit, Thank You!");
                return;
            }

            /** Get the selected profile
             *
             */
            Profile profile = null;

            for (Profile p : CustomerProfile.profiles) {
                if (p.toString().equals(list1.getSelectedValue())) {
                    profile = p;
                    break;
                }
            }

            /** If no profile selected,
             *
             * do nothing
             *
             */
            if (profile == null)
                return;

            /** If profile selected :
             * Show Customer Profile Detail
             * with selected profile data
             *
             */
            JFrame frame = new JFrame("CustomerProfileDetail");
            frame.setContentPane(new CustomerProfileDetail(this, profile).panel1);
            frame.setSize(500, 300);
            frame.setVisible(true);
            frame.pack();
        });

        /** Delete Profile button clicked
         *
         *  to activate : add action listener.
         */
        deleteProfileButton.addActionListener(e -> {
            /** Check owner user select the item in the list
             *
             */
            if (list1.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Please select profile to delete , Thank you!");
                return;
            }

            /** Get the selected customer profile
             *
             */
            Profile profile = null;

            for (Profile p : CustomerProfile.profiles) {
                if (p.toString().equals(list1.getSelectedValue())) {
                    profile = p;
                    break;
                }
            }

            /** If no profile selected,
             * do nothing
             *
             */
            if (profile == null)
                return;

            /** Check is customer  profile has
             * balance bigger than 0
             * ( the system allows the owner user to delete any profile only when
             * balance is 0 )
             *
             */
            if (profile.getBalance() > 0) {
                JOptionPane.showMessageDialog(null, "You can only delete the profile has 0 balance ," +
                        " Thank you!");
                return;
            }

            /**When deleting a profile, system warns the users that all associated information
             * will be deleted.
             * If balance is 0, it ask to confirm for invoices
             *
             * YES-NO option.
             */
            int reply = JOptionPane.showConfirmDialog(null,
                    "All associated invoices will be deleted, is that OK ? ",
                    "Delete Profile", JOptionPane.YES_NO_OPTION);
            /**Yes clicked,
             *  delete profile and refresh view
             *
             */
            if (reply == JOptionPane.YES_OPTION) {
                CustomerProfile.profiles.remove(profile);
                txtSearchField.setText("");
                updateView();
            }
        });

        /**Search button clicked
         *
         * System allows to search for a customer profile
         * by id or name.
         *
         * to activate search button : Add action listener.
         */
        searchButton.addActionListener(e -> {
            /**Get search String
             *
             */
            String searchStr = txtSearchField.getText();

            /**If search string is empty, show all
             *
             */
            if (searchStr.isEmpty()) {
                updateView();
                return;
            }

            /**Get item list
             * with the search string
             *
             */
            ArrayList<String> result = new ArrayList<>();
            for (Profile p : CustomerProfile.profiles) {
                /**Check if :
                 * customer name contains search string
                 *
                 */
                if (p.getCustomerName().contains(searchStr))
                    result.add(p.toString());
                /**Check if :
                 * customer ID contains search string
                 *
                 */
                else if (String.valueOf(p.getCustomerID()).contains(searchStr))
                    result.add(p.toString());
            }

            /**If nothing found from the search result
             *  show an alert
             *
             */
            if (result.size() == 0) {
                JOptionPane.showMessageDialog(null, "Sorry! Customer Profile can not be found. ");
                list1.setListData(new String[]{});
            } else {
                String[] data = result.toArray(new String[0]);
                list1.setListData(data);
            }
        });

        /**System allows owner user to see a list
         * of customers in the system.
         * when View all button is clicked.
         *
         * to activate: add action listener.
         */
        viewAllButton.addActionListener(e -> {
            /**Remove search string and update
             *
             */
            txtSearchField.setText("");
            updateView();
        });

        /** Exit button clicked to close program
         *add action listener.
         */
        exitButton.addActionListener(e -> {
            System.exit(1);
        });
    }

    /**
     * Crete instance.
     *
     * Pattern.
     */
    private static final TestCustomerProfile instance = new TestCustomerProfile();

    public static TestCustomerProfile getInstance(){
        return instance;
    }

    /** Update the customer profile list
     *  from saved profiles
     * method updateView.
     */
    public void updateView() {
        /** If profiles are empty,
         * show empty list
         *
         */
        if(CustomerProfile.profiles.size() == 0) {
            String[] result = new String[]{};
            list1.setListData(result);
        }
        /**
         *
         * The list should show the full name, phone number,
         * balance and Last amount paid.
         */
        else {
            /** Get string of profile (Name, phone, balance, last paid amount) from profile list
             *
             */
            String[] result = new String[CustomerProfile.profiles.size()];
            for (int i = 0; i < result.length; i++)
                result[i] = CustomerProfile.profiles.get(i).toString();
            list1.setListData(result);
        }
    }

    /** Main function to show the  TestCustomerProfile
     *
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("TestCustomerProfile");
        frame.setContentPane(getInstance().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 300);
        frame.setVisible(true);
    }

} //end of class
