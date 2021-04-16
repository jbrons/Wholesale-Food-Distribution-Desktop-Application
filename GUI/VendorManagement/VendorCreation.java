package GUI.VendorManagement;

import src.User.UserDatabase;
import src.Vendor.StateAbbrs;
import src.Vendor.VendorList;
import src.Vendor.Vendor;
import src.Vendor.DateValidator;
import GUI.MainWindow.MainWindowGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *  This class implements the Vendor Creation GUI for owner and purchaser users.
 *  It handles creating and updating the Vendor list.
 *
 * @author Jordan Bronstetter
 * @date 03/18/2021
 *
 */
public class VendorCreation implements ActionListener, KeyListener {
    private JPanel rootPanel;

    protected JLabel lblFullName;
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

    private VendorList vendorList = VendorList.getInstance();
    private ListModel displayModel = ListModel.getInstance();
    private Vendor vendor = null;

    private String name, streetAddress, city, phoneNum;
    private StateAbbrs state;
    private double balance, lastPaidAmount;
    private LocalDate lastOrderDate, seasonalDiscDate;
    private NumberFormat numberFormat = NumberFormat.getInstance();
    private NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private DateValidator validator = new DateValidator(dateFormat);
    private UserDatabase database = UserDatabase.getInstance();

    MainWindowGUI mainWindowGUI;

    public VendorCreation() {
        mainWindowGUI = MainWindowGUI.getInstance();
        mainWindowGUI.setTitle("Create Vendor");
        addListeners();
        setUpGUI();
    }

    public VendorCreation(Vendor vendor) {
        this();
        this.vendor = vendor;
        mainWindowGUI.setTitle("Update Vendor");
        initializeInputs(vendor);
    }

    private void setUpGUI() {
        String phoneFormat = "###-###-####";
        String dateFormat = "##/##/####";

        cboState.setEditable(false);
        txtPhoneNum.setFormatterFactory(new DefaultFormatterFactory(formatter(phoneFormat, ' ')));
        txtBalance.setFormatterFactory(new DefaultFormatterFactory(numberFormatter));
        txtLastPaidAmount.setFormatterFactory(new DefaultFormatterFactory(numberFormatter));
        txtLastOrderDate.setFormatterFactory(new DefaultFormatterFactory(formatter(dateFormat, '-')));
        txtSeasonalDiscDate.setFormatterFactory(new DefaultFormatterFactory(formatter(dateFormat, '-')));
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
            displayError("Full Name" + message);
            return false;
        } else {
            if (vendorList.searchVendorList(txtFullName.getText()) > -1 && vendor == null) {
                displayError(txtFullName.getText() + " already has a profile");
                return false;
            }
            name = txtFullName.getText();
        }

        if (txtStreetAddress.getText().isEmpty()) {
            displayError("Street Address" + message);
            return false;
        } else {
            streetAddress = txtStreetAddress.getText();
        }

        if (txtCity.getText().isEmpty()) {
            displayError("City" + message);
            return false;
        } else {
            city = txtCity.getText();
        }

        if (cboState.getSelectedIndex() < 0) {
            displayError("State" + message);
            return false;
        } else {
            state = (StateAbbrs) cboState.getSelectedItem();
        }

        if (!txtPhoneNum.isEditValid()) {
            displayError("Phone number" + message);
            return false;
        } else {
            phoneNum = txtPhoneNum.getText();
        }

        if (!txtBalance.getText().isEmpty()) {
            balance = getNumber(txtBalance.getText());
            if (balance == -1) {
                return false;
            }
        } else {
            balance = 0 ;
        }

        if (!txtLastPaidAmount.getText().isEmpty()) {
            lastPaidAmount = getNumber(txtLastPaidAmount.getText());
            if (lastPaidAmount == -1) {
                return false;
            }
        } else {
            lastPaidAmount = 0;
        }

        if (!txtLastOrderDate.isEditValid()) {
            displayError("Last Order Date" + message);
            return false;
        } else {
            lastOrderDate = validator.getDate(txtLastOrderDate.getText());
            if (lastOrderDate == null) {
                displayError("Incorrect Last Order Date");
                return false;
            }
        }

        if (!txtSeasonalDiscDate.isEditValid()) {
            displayError("Seasonal Discount Start Date" + message);
            return false;
        } else {
            seasonalDiscDate = validator.getDate(txtSeasonalDiscDate.getText());
            if (seasonalDiscDate == null) {
                displayError("Incorrect Seasonal Discount Start Date");
                return false;
            }
        }
        return true;
    }

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

    private MaskFormatter formatter(String format, char placeHolder) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(format);
            formatter.setPlaceholderCharacter(placeHolder);
        } catch (ParseException e) {
            System.out.println("Format Error");
        }
        return formatter;
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
                    vendorList.updateVendor(vendor);
                    System.out.println(vendorList.searchVendorList(vendor.getId()));
                    displayModel.updateVendor(vendor.toString(), vendorList.searchVendorList(vendor.getId()));
                } else {
                    Vendor vendor = new Vendor(name, streetAddress, city, state, phoneNum, balance,
                            lastPaidAmount, lastOrderDate, seasonalDiscDate);
                    vendorList.addVendor(vendor);
                   displayModel.addVendor(vendor.toString());
                }
                closeCreationGUI();
            }
        } else if (userAction == btnCancel) {
            closeCreationGUI();
        }
    }

    public void closeCreationGUI() {
        mainWindowGUI.setJPanel(new VendorUI().getPanel());
    }

    private double getNumber(String numStr) {
        try {
            Number number = numberFormat.parse(numStr);
            return number.doubleValue();
        } catch (ParseException  e) {
            return -1;
        }
    }

    private void displayError(String message) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
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

            // for txtBalance
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