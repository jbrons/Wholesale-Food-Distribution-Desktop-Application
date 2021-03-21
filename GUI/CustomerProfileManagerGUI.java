package GUI;

import Profile.CustomerProfile;
import Profile.CustomerProfileDatabase;

import javax.swing.*;
import java.util.ArrayList;

public class CustomerProfileManagerGUI {

    CustomerProfileDatabase database;

    /** UI Components
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
    MainWindowGUI mainWindowGUI;

   /** Constructor
   */
    CustomerProfileManagerGUI() {

        mainWindowGUI = MainWindowGUI.getInstance();
        database = CustomerProfileDatabase.getInstance();

        initUI();

        updateView();
    }
    private void initUI() {
        /** Add new profile button clicked
        */
        addNewProfileButton.addActionListener(e -> {
            mainWindowGUI.setJPanel(new DetailCustomerProfileGUI().getPanel());
        });

        /** Edit Profile button clicked
        */
        editProfileButton.addActionListener(e -> {
            /**Check owner user select the item in the list
            */
            if (list1.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Please select profile to edit");
                return;
            }

            /** Get selected profile
            */
            CustomerProfile profile = null;

            for (CustomerProfile p : database.getAllProfiles()) {
                if (p.toString().equals(list1.getSelectedValue())) {
                    profile = p;
                    break;
                }
            }

            /** If no profile selected, do nothing
            */
            if (profile == null)
                return;

            /**Show DetailProfile with selected profile data
            */
            mainWindowGUI.setJPanel(new DetailCustomerProfileGUI(profile).getPanel());
        });

        /** Delete Profile button clicked
        */
        deleteProfileButton.addActionListener(e -> {
            /** Check owner user select the item in the list
            */
            if (list1.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Please select profile to delete");
                return;
            }

            /** Get selected profile
            */
            CustomerProfile profile = null;

            for (CustomerProfile p : database.getAllProfiles()) {
                if (p.toString().equals(list1.getSelectedValue())) {
                    profile = p;
                    break;
                }
            }

            /** If no profile selected, do nothing
            */
            if (profile == null)
                return;

            /** Check profile has balance bigger than 0
            */
            if (profile.getBalance() > 0) {
                JOptionPane.showMessageDialog(null, "You can delete the profile has 0 balance.");
                return;
            }

            /** If balance is 0, it ask to confirm for invoices
            */
            int reply = JOptionPane.showConfirmDialog(null,
                    "All associated invoices will be deleted, is it ok?", "Delete Profile", JOptionPane.YES_NO_OPTION);
            /**Yes clicked, delete profile and refresh view
            */
            if (reply == JOptionPane.YES_OPTION) {
                database.deleteProfile(profile);
                txtSearchField.setText("");
                updateView();
            }
        });

        /** Search button clicked
        */
        searchButton.addActionListener(e -> {
            /** Get search String
            */
            String searchStr = txtSearchField.getText();

            /** If search string is empty, show all
            */
            if (searchStr.isEmpty()) {
                updateView();
                return;
            }

            /** Get item list with search string
            */
            ArrayList<String> result = new ArrayList<>();
            for (CustomerProfile p : database.getAllProfiles()) {
                /** Check customer name contains search string
                */
                if (p.getCustomerName().contains(searchStr))
                    result.add(p.toString());
                /** Check customer ID contains search string
                */
                else if (String.valueOf(p.getCustomerID()).contains(searchStr))
                    result.add(p.toString());
            }

            /**If no search result, show alert
            */
            if (result.size() == 0) {
                JOptionPane.showMessageDialog(null, "Cannot find the profile");
                list1.setListData(new String[]{});
            } else {
                String[] data = result.toArray(new String[0]);
                list1.setListData(data);
            }
        });

        /** View all button clicked
        */
        viewAllButton.addActionListener(e -> {
            /**Remove search string and update
            */
            txtSearchField.setText("");
            updateView();
        });

        /** Exit button clicked, close program
        */
        exitButton.addActionListener(e -> {
            mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());
        });
    }

    /**Update list from saved profiles
    */
    public void updateView() {
        /**If profiles are empty, show empty list
        */
        CustomerProfile[] profiles = database.getAllProfiles();
        if (profiles.length == 0) {
            String[] result = new String[]{};
            list1.setListData(result);
        } else {
            /** Get string of profile (Name, phone, balance, lastpaidamount) from profile list
            */
            String[] result = new String[profiles.length];
            for (int i = 0; i < result.length; i++)
                result[i] = profiles[i].toString();
            list1.setListData(result);
        }
    }

    public JPanel getPanel() {
        return panel1;
    }
}
