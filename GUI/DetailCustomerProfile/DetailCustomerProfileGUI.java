package GUI.DetailCustomerProfile;
/**
 *
 *Author : @Joyshree Chowdhury
 */

import GUI.CustomerProfileManager.CustomerProfileManagerGUI;
import GUI.MainWindow.MainWindowGUI;
import src.Customer.CompanyCustomerProfile;
import src.Customer.CustomerProfile;
import src.Customer.CustomerProfileDatabase;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailCustomerProfileGUI {
    /**UI components
     *
     */
    private JPanel panel1;
    private JPanel Buttons;
    private JButton OKButton;
    private JButton cancelButton;
    private JTextField txtCustomerID;
    private JTextField txtCustomerName;
    private JTextField txtStreetAddress;
    private JTextField txtCity;
    private JComboBox<String> cmbState;
    private JFormattedTextField txtPhone;
    private JFormattedTextField txtBalance;
    private JFormattedTextField txtLastPaidAmount;
    private JFormattedTextField txtLastOrderDate;

    /**flag for create or edit profile
     *
     */
    boolean isCreate = true;
    MainWindowGUI mainWindowGUI;
    CustomerProfileDatabase database;

    /**Constructor for create profile
     *
     */
    public DetailCustomerProfileGUI() {
        mainWindowGUI = MainWindowGUI.getInstance();
        database = CustomerProfileDatabase.getInstance();
        initialize(null);
    }

    /**Constructor for edit profile
     *
     * @param profile
     */
    public DetailCustomerProfileGUI(CustomerProfile profile)
    {
        mainWindowGUI = MainWindowGUI.getInstance();
        database = CustomerProfileDatabase.getInstance();
        initialize(profile);
    }

    /**
     *
     * @return
     */
    public JPanel getPanel()
    {

        return panel1;
    }

    /**Initialize ui components with profile
     *
     * @param profile
     */
    private void initialize(CustomerProfile profile) {
        /**Set states to combobox to select state
         *
         */
        for (String s : CompanyCustomerProfile.STATES)
            cmbState.addItem(s);

        /** customer name has 20 length
         *
         */
        txtCustomerName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (txtCustomerName.getText().length() >= 20 )
                    e.consume();
            }
        });

        /** street address has 20 length
         *
         */
        txtStreetAddress.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (txtStreetAddress.getText().length() >= 20 )
                    e.consume();
            }
        });

        /** city has 20 length
         *
         */
        txtCity.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (txtCity.getText().length() >= 20 )
                    e.consume();
            }
        });

        /** Phone number style
         *
         */
        txtPhone.setFormatterFactory(new DefaultFormatterFactory(createFormatter("###-###-####")));
        /** Balance and last paid amount should be number
         *
         */
        txtBalance.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getInstance())));
        txtLastPaidAmount.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getInstance())));
        /**LastOrderDate should be date format
         *
         */
        txtLastOrderDate.setFormatterFactory(new DefaultFormatterFactory(createFormatter("##/##/####")));

        /**Create new profile case
         *
         */
        if (profile == null) {
            isCreate = true;
            txtCustomerID.setText(String.valueOf(CompanyCustomerProfile.ID + 1));
            /** Edit profile case
             *
             */
        } else {
            isCreate = false;
            txtCustomerID.setText(String.valueOf(profile.getCustomerID()));
            txtCustomerName.setText(profile.getCustomerName());
            txtStreetAddress.setText(profile.getStreetAddress());
            txtCity.setText(profile.getCity());
            cmbState.setSelectedItem(profile.getState());
            txtPhone.setText(profile.getPhone());
            txtBalance.setText(String.valueOf(profile.getBalance()));
            txtLastPaidAmount.setText(String.valueOf(profile.getLastPaidAmount()));
            txtLastOrderDate.setText(profile.getLastOrderDate());
        }

        /** OK button clicked
         *
         */
        OKButton.addActionListener(e -> {
            /** Validate all data is correct
             *
             */
            if (!validateForm())
                return;

            /**Create new profile and add it to list
             *
             */
            if (isCreate) {
                float balance = 0;
                float amount = 0;
                try {
                    String temp = txtBalance.getText();
                    temp = temp.replaceAll(",", "");
                    balance = Float.parseFloat(temp);
                    temp = txtLastPaidAmount.getText();
                    temp = temp.replaceAll(",", "");
                    amount = Float.parseFloat(temp);
                } catch (Exception ignored) {}
                database.addProfile(new CompanyCustomerProfile(txtCustomerName.getText(),
                        txtStreetAddress.getText(),
                        txtCity.getText(),
                        (String)cmbState.getSelectedItem(),
                        txtPhone.getText(),
                        balance,
                        amount,
                        txtLastOrderDate.getText()));
            } else if (profile != null){
                /** Edit profile
                 *
                 */
                float balance = 0;
                float amount = 0;
                try {
                    String temp = txtBalance.getText();
                    temp = temp.replaceAll(",", "");
                    balance = Float.parseFloat(temp);
                    temp = txtLastPaidAmount.getText();
                    temp = temp.replaceAll(",", "");
                    amount = Float.parseFloat(temp);
                } catch (Exception ignored) {}
                profile.setCustomerName(txtCustomerName.getText());
                profile.setStreetAddress(txtStreetAddress.getText());
                profile.setCity(txtCity.getText());
                profile.setState(String.valueOf(cmbState.getSelectedItem()));
                profile.setPhone(txtPhone.getText());
                profile.setBalance(balance);
                profile.setLastPaidAmount(amount);
                profile.setLastOrderDate(txtLastOrderDate.getText());

                database.deleteProfile(profile);
                database.addProfile(profile);
            }

            /** Close detail profile
             *
             */
            mainWindowGUI.setJPanel(new CustomerProfileManagerGUI().getPanel());
        });

        /**Cancel button clicked
         *
         */
        cancelButton.addActionListener(e -> {
            /**Close detail profile page
             *
             */
            mainWindowGUI.setJPanel(new CustomerProfileManagerGUI().getPanel());
        });
    }

    /** Create formatter for formatted text
     *
     * @param s
     * @return
     */
    private MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }

    /** Validate data are equal
     *
     * @return
     */
    private boolean validateForm() {
        for (CustomerProfile profile : database.getAllProfiles()) {
            if (txtCustomerID.getText().equals(String.valueOf(profile.getCustomerID())))
                continue;
            /** Customer Name should be different
             *
             */
            if (profile.getCustomerName().equals(txtCustomerName.getText())) {
                JOptionPane.showMessageDialog(null, "Same customer name in the list.");
                return false;
            }
            if (txtCustomerName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "You should input customer name.");
                return false;
            }
        }

        /** Street should have value
         *
         */
        if (txtStreetAddress.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "You should input customer's street address.");
            return false;
        }
        /** City should have value
         *
         */
        if (txtCity.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "You should input customer's city.");
            return false;
        }
        /**State should have value
         *
         */
        if (cmbState.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "You should select customer' state.");
            return false;
        }
        /** Phone should have value
         *
         */
        if (txtPhone.getText().contains(" ")) {
            JOptionPane.showMessageDialog(null, "You should input customer phone number.");
            return false;
        }
        /** LastOrderDate should have value
         *
         */
        SimpleDateFormat fromUser = new SimpleDateFormat("MM/dd/yyyy");
        Date date;
        try {
            date = fromUser.parse(txtLastOrderDate.getText());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Please input correct date");
            return false;
        }

        /**Check past date
         *
         */
        if (date.getTime() < new Date().getTime()) {
            JOptionPane.showMessageDialog(null, "It isnt allowed to use past date");
            return false;
        }

        /** All true
         *
         */
        return true;
    }
}
