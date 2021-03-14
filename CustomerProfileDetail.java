/**
 *
 * @Author : Joyshree Chowdhury
 *
 *
 */

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

public class CustomerProfileDetail {
    /** GUI components
     *
     */
    public JPanel panel1;
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

    /** flag for create or edit profile
     *
     */
    boolean isCreate = true;

    /** Parent view
     *
     */
    TestCustomerProfile view;

    /** Constructor
     *
     * to create a customer profile
     *
     * @param parent
     */
    CustomerProfileDetail(TestCustomerProfile parent) {
        initialize(null);
        view = parent;
    }

    /** Constructor for editing
     * customer profile
     *
     * @param parent
     * @param profile
     */
    CustomerProfileDetail(TestCustomerProfile parent, Profile profile)
    {
        initialize(profile);
        view = parent;
    }

    /**Initializing all the GUI components with profile
     *
     * @param profile
     */
    private void initialize(Profile profile) {
        /**Set states to select state
         * from ComboBox Strings.
         *
         * (cmbstate)
         *
         */
        for (String s : CustomerProfile.STATES)
            cmbState.addItem(s);

        /**customer name has max 20 characters.
         * add key listener
         */
        txtCustomerName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (txtCustomerName.getText().length() >= 20 )
                    e.consume();
            }
        });

        /** street address has 20 max characters
         * add key listener
         *
         */
        txtStreetAddress.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (txtStreetAddress.getText().length() >= 20 )
                    e.consume();
            }
        });

        /**city has 20 max characters
         * add key listener
         *
         */
        txtCity.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (txtCity.getText().length() >= 20 )
                    e.consume();
            }
        });

        /** Phone number style is:
         *
         * [xxx-xxx-xxxx]
         */
        txtPhone.setFormatterFactory(new DefaultFormatterFactory(createFormatter("###-###-####")));
        /**Balance and last paid amount should be
         * Decimal numbers
         *
         */
        txtBalance.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getInstance())));
        txtLastPaidAmount.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getInstance())));
        /**LastOrderDate should be date format
         * [ mm / dd / yyyy ]
         *
         */
        txtLastOrderDate.setFormatterFactory(new DefaultFormatterFactory(createFormatter("##/##/####")));

        /** Create a new customer profile
         *
         */
        if (profile == null) {
            isCreate = true;
            txtCustomerID.setText(String.valueOf(CustomerProfile.ID + 1));
            /** Edit customer  profile
             *
             */
        }
        else {
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

        /** OK( done with customer profile editing )
         * button clicked
         *add action listener
         */
        OKButton.addActionListener(e -> {
            /** Validate all data is correct
             *
             */
            if (!validateForm())
                return;

            /**Create new profile and
             *  add it to the customer list
             *
             */
            if (isCreate) {
                float balance = 0;
                float amount = 0;
                /**
                 * Exception  Possible.
                 */
                try {
                    balance = Float.parseFloat(txtBalance.getText());
                    amount = Float.parseFloat(txtLastPaidAmount.getText());
                }
                catch (Exception ignored) {}
                CustomerProfile.profiles.add(new CustomerProfile(txtCustomerName.getText(),
                        txtStreetAddress.getText(),
                        txtCity.getText(),
                        (String)cmbState.getSelectedItem(),
                        txtPhone.getText(),
                        balance,
                        amount,
                        txtLastOrderDate.getText()));
            }
            else if (profile != null){
                /** Edit customer profile
                 *
                 */
                float balance = 0;
                float amount = 0;
                /**
                 * Exception possible.
                 */
                try {
                    balance = Float.parseFloat(txtBalance.getText());
                    amount = Float.parseFloat(txtLastPaidAmount.getText());
                } catch (Exception ignored) {}
                profile.setCustomerName(txtCustomerName.getText());
                profile.setStreetAddress(txtStreetAddress.getText());
                profile.setCity(txtCity.getText());
                profile.setState(String.valueOf(cmbState.getSelectedItem()));
                profile.setPhone(txtPhone.getText());
                profile.setBalance(balance);
                profile.setLastPaidAmount(amount);
                profile.setLastOrderDate(txtLastOrderDate.getText());
            }

            /** Update parent's list view
             * According to new customer profiles.
             *
             */
            this.view.updateView();
            /** Close detail of the customer  profile
             *
             */
            SwingUtilities.getWindowAncestor(this.panel1).dispose();
        });

        /** Cancel button clicked
         *
         *  add action listener .
         */
        cancelButton.addActionListener(e -> {
            /**Close detail of the
             * customer profile page
             *
             */
            SwingUtilities.getWindowAncestor(this.panel1).dispose();
        });
    }

    /** Create formatter for
     * the formatted text
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

    /** to Validate
     * that all the  data are equal
     *
     * @return
     */
    private boolean validateForm() {
        for (Profile profile : CustomerProfile.profiles) {
            if (txtCustomerID.getText().equals(String.valueOf(profile.getCustomerID())))
                continue;

            /**The system will restrict adding
             * duplicate customer names.
             * Customer Name should be different.
             *
             */
            if (profile.getCustomerName().equals(txtCustomerName.getText())) {
                JOptionPane.showMessageDialog(null, "Same customer name in the list.");
                return false;
            }
            if (txtCustomerName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "You should input customer name, Thank you!");
                return false;
            }
        }

        /**Street address  field
         *  should have value
         *
         */
        if (txtStreetAddress.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "You should input customer's street address, Thank you!");
            return false;
        }
        /** City field
         *  should have value
         *
         */
        if (txtCity.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "You should input customer's city, Thank you! ");
            return false;
        }
        /**State field
         * should have value
         *
         */
        if (cmbState.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "You should select customer' state, Thank you! ");
            return false;
        }
        /**Phone field
         * should have value
         *
         */
        if (txtPhone.getText().contains(" ")) {
            JOptionPane.showMessageDialog(null, "You should input customer phone number, Thank you!");
            return false;
        }
        /** LastOrderDate field
         * should have value
         *
         */
        SimpleDateFormat fromUser = new SimpleDateFormat("MM/dd/yyyy");
        Date date;
        /**
         * Exception possible for wrong input.
         */
        try {
            date = fromUser.parse(txtLastOrderDate.getText());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Please input correct date , Thank you!");
            return false;
        }

        /**Check if the date is
         * past date
         *
         * system restrict adding past date values.
         */
        if (date.getTime() < new Date().getTime()) {
            JOptionPane.showMessageDialog(null, "It is not allowed to use past date");
            return false;
        }

        /** All inputs are true
         *
         */
        return true;
    }
} //end of class
