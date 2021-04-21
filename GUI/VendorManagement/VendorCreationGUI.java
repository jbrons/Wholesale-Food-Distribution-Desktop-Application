package GUI.VendorManagement;

import GUI.Login.LoginGUI;
import GUI.PurchaseOrderManagement.DialogDisplay;
import src.Vendor.*;
import GUI.MainWindow.MainWindowGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.text.DefaultFormatterFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *  VendorCreation implements the Vendor Creation GUI for owner and purchaser users.
 *  It handles creating and updating the Vendor list.
 *
 * @author Jordan Bronstetter
 * @date 03/18/2021
 *
 */
public class VendorCreationGUI implements ActionListener, KeyListener {
    private JPanel rootPanel;

    private JLabel lblFullName;
    private JLabel lblStreetAddress;
    private JLabel lblCity;
    private JLabel lblState;
    private JLabel lblPhoneNum;
    private JLabel lblBalance;
    private JLabel lblLastPaidAmount;
    private JLabel lblLastOrderDate;
    private JLabel lblSeasonalDiscDate;

    private JTextField txtFullName;
    private JTextField txtStreetAddress;
    private JTextField txtCity;
    private JFormattedTextField txtPhoneNum;
    private JFormattedTextField txtBalance;
    private JFormattedTextField txtLastPaidAmount;
    private JFormattedTextField txtLastOrderDate;
    private JFormattedTextField txtSeasonalDiscDate;

    private JComboBox cboState;
    private JButton btnCreate;
    private JButton btnCancel;
    private JButton btnLogOut;

    private static VendorDatabase vendorDatabase = VendorDatabase.getInstance();
    private static VendorModel displayModel = VendorModel.getInstance();
    private Vendor vendor = null;

    private String name, streetAddress, city, phoneNum;
    private StateAbbrs state;
    private double balance, lastPaidAmount;
    private LocalDate lastOrderDate, seasonalDiscDate;
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private DateValidator validator = new DateValidator(dateFormat);

    MainWindowGUI mainWindowGUI;

    public VendorCreationGUI() {
        mainWindowGUI = MainWindowGUI.getInstance();
        mainWindowGUI.setTitle("Create Vendor");
        addListeners();
        setUpGUI();
    }

    public VendorCreationGUI(Vendor vendor) {
        this();
        this.vendor = vendor;
        mainWindowGUI.setTitle("Update Vendor");
        initializeInputs(vendor);
    }

    private void setUpGUI() {
        String phoneFormat = "###-###-####";
        String dateFormat = "##/##/####";

        txtFullName.requestFocusInWindow();
        cboState.setEditable(false);
        txtPhoneNum.setFormatterFactory(new DefaultFormatterFactory(
                ParseStrings.formatter(phoneFormat, ' ')));

        txtBalance.setFormatterFactory(new DefaultFormatterFactory(ParseStrings.getNumberFormatter()));
        txtLastPaidAmount.setFormatterFactory(new DefaultFormatterFactory(ParseStrings.getNumberFormatter()));

        txtLastOrderDate.setFormatterFactory(new DefaultFormatterFactory(
                ParseStrings.formatter(dateFormat, '-')));
        txtSeasonalDiscDate.setFormatterFactory(new DefaultFormatterFactory(
                ParseStrings.formatter(dateFormat, '-')));
        cboState.setModel(new DefaultComboBoxModel(StateAbbrs.values()));
    }

    private void initializeInputs(Vendor vendor) {
        btnCreate.setText("Update");
        txtFullName.setText(vendor.getName());
        txtStreetAddress.setText(vendor.getStreetAddress());
        txtCity.setText(vendor.getCity());
        cboState.setSelectedItem(vendor.getState());
        txtPhoneNum.setText(vendor.getPhoneNum());
        txtBalance.setText(String.valueOf(vendor.getBalance()));
        txtLastPaidAmount.setText(String.valueOf(vendor.getLastPaidAmount()));
        txtLastOrderDate.setText(vendor.getLastOrderDate().format(dateFormat));
        txtSeasonalDiscDate.setText(vendor.getSeasonalDiscDate().format(dateFormat));
        txtFullName.requestFocusInWindow();
    }

    /**
     * Checks if all the inputs are valid and returns false at the first invalid input
     *
     * @return false if there are invalid inputs, otherwise true
     * */
    private boolean getInputs() {
        String message = " cannot be blank.";

        if (txtFullName.getText().isEmpty()) {
            DialogDisplay.displayError("Full Name" + message);
            return false;
        } else {
            if (vendorDatabase.searchVendorDatabase(txtFullName.getText()) > -1 && vendor == null) {
                DialogDisplay.displayError(txtFullName.getText() + " already has a profile");
                return false;
            }
            name = txtFullName.getText();
        }

        name = VendorCreation.checkText(txtFullName);
        if (name == null) {
            DialogDisplay.displayError("Full Name" + message);
            return false;
        } else if (vendorDatabase.searchVendorDatabase(txtFullName.getText()) > -1 && vendor == null) {
            DialogDisplay.displayError(txtFullName.getText() + " already has a profile");
            return false;
        }

        streetAddress = VendorCreation.checkText(txtStreetAddress);
        if (streetAddress == null) {
            DialogDisplay.displayError("Street Address" + message);
            return false;
        }

        city = VendorCreation.checkText(txtCity);
        if (city == null) {
            DialogDisplay.displayError("City" + message);
            return false;
        }

        if (cboState.getSelectedIndex() < 0) {
            DialogDisplay.displayError("State" + message);
            return false;
        } else {
            state = (StateAbbrs) cboState.getSelectedItem();
        }

        if (!txtPhoneNum.isEditValid()) {
            DialogDisplay.displayError("Phone number" + message);
            return false;
        } else {
            phoneNum = txtPhoneNum.getText();
        }

        balance = VendorCreation.checkNumber(txtBalance);
        if (balance == -1) {
            return false;
        }

        lastPaidAmount = VendorCreation.checkNumber(txtLastPaidAmount);
        if (lastPaidAmount == -1) {
            return false;
        }

        lastOrderDate = VendorCreation.checkDate(txtLastOrderDate);
        if (lastOrderDate == null) {
            DialogDisplay.displayError("Incorrect Last Order Date");
            return false;
        }

        seasonalDiscDate = VendorCreation.checkDate(txtSeasonalDiscDate);
        if (seasonalDiscDate == null) {
            DialogDisplay.displayError("Incorrect Seasonal Discount Start Date");
            return false;
        }

        return true;
    }

    /**
     * Initializes inputs when updating an item
     */
    private void updateInputs() {
        vendor.setName(name);
        vendor.setStreetAddress(streetAddress);
        vendor.setCity(city);
        vendor.setState(state);
        vendor.setPhoneNum(phoneNum);
        vendor.setBalance(balance);
        vendor.setLastPaidAmount(lastPaidAmount);
        vendor.setLastOrderDate(lastOrderDate);
        vendor.setSeasonalDiscDate(seasonalDiscDate);
    }

    public JPanel getPanel() {
        return rootPanel;
    }

    /**
     * Invoked to add an ActionListener and a KeyListener to JTextFields and JFormattedTextFields
     * and an ActionListener to Jbuttons
     *
     */
    private void addListeners() {
        Component[] components = rootPanel.getComponents();

        for (Component component : components) {
            if (component instanceof JTextField)  {
                component.addKeyListener(this);
            } else if (component instanceof JFormattedTextField) {
                component.addKeyListener(this);
            } else if (component instanceof JButton) {
                ((JButton) component).addActionListener(this);
            }
        }
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object userAction = e.getSource();

        if (userAction == btnCreate) {
            if (getInputs()) {
                if (vendor != null) {
                    updateInputs();
                    vendorDatabase.updateVendor(vendor);
                    System.out.println(vendorDatabase.searchVendorDatabase(vendor.getId()));
                    displayModel.update(vendor.toString(), vendorDatabase.searchVendorDatabase(vendor.getId()));
                } else {
                    Vendor vendor = new Vendor(name, streetAddress, city, state, phoneNum, balance,
                            lastPaidAmount, lastOrderDate, seasonalDiscDate);
                    vendorDatabase.addVendor(vendor);
                   displayModel.add(vendor.toString());
                }
                closeCreationGUI();
            }
        } else if (userAction == btnCancel) {
            closeCreationGUI();
        } else if (userAction == btnLogOut) {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        }
    }

    public void closeCreationGUI() {
        mainWindowGUI.setJPanel(new VendorHubGUI().getPanel());
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        Object action = e.getSource();

        int maxChars = 20;
        if ((action == txtFullName) || (action == txtStreetAddress) || (action == txtCity)) {
            if (((JTextField) action).getText().length() >= maxChars) {
                e.consume();
            }
        } else if ((action == txtBalance) || (action == txtLastPaidAmount)) {
            String text = ((JFormattedTextField) action).getText();
            int decimalIndex = text.indexOf('.');

            if (e.getKeyChar() == '.') {
                if (decimalIndex != -1) {
                    e.consume();
                }
            } else if (Character.isDigit(e.getKeyChar())) {
                if (decimalIndex > -1) {
                    if (text.substring(decimalIndex + 1).length() >= 2) {
                        e.consume();
                    }
                }
            } else {
                e.consume();
            }
        }
    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {}

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {}
}