package GUI.VendorManagement;

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

public class VendorCreation<formatter> implements ActionListener, KeyListener {
    //private JFrame mainFrame;
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

    VendorList vendorList = VendorList.getInstance();
    //DefaultFormatterFactory formatterFactory = new DefaultFormatterFactory(dataFormatter);
    DisplayList displayList = DisplayList.getInstance();

    private String name, streetAddress, city, phoneNum;
    private StateAbbrs state;
    private double balance, lastPaidAmount;
    private LocalDate lastOrderDate, seasonalDiscDate;
    NumberFormat numberFormat = NumberFormat.getInstance();
    NumberFormatter numberFormatter = new NumberFormatter(numberFormat);

    MainWindowGUI mainWindowGUI;

    public VendorCreation() {
        mainWindowGUI = MainWindowGUI.getInstance();

        //mainFrame = new JFrame("Application.Vendor Application.Profile Creation");
        String phoneFormat = "###-###-####";
        String dateFormat = "##/##/####";
        addListeners();

        txtPhoneNum.setFormatterFactory(new DefaultFormatterFactory(formatter(phoneFormat, ' ')));
        txtBalance.setFormatterFactory(new DefaultFormatterFactory(numberFormatter));
        txtLastPaidAmount.setFormatterFactory(new DefaultFormatterFactory(numberFormatter));
        //txtLastOrderDate.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(new SimpleDateFormat("mm/dd/yyyy"))));
        txtLastOrderDate.setFormatterFactory(new DefaultFormatterFactory(formatter(dateFormat, '-')));
        txtSeasonalDiscDate.setFormatterFactory(new DefaultFormatterFactory(formatter(dateFormat, '-')));
        cboState.setModel(new DefaultComboBoxModel(StateAbbrs.values()));  /// MOOOVEEEEE SOMEPLACE ELSE
    }

    public VendorCreation(Vendor vendor) {
        initializeInputs(vendor);
    }

    private void initializeInputs(Vendor vendor) {
        txtFullName.setText(vendor.getName());
        txtStreetAddress.setText(vendor.getStreetAddress());
        txtCity.setText(vendor.getCity());
        cboState.setSelectedItem(vendor.getState());
        txtPhoneNum.setText(vendor.getPhoneNum());
        txtBalance.setValue(String.valueOf(vendor.getBalance()));
        txtLastPaidAmount.setText(String.valueOf(vendor.getLastPaidAmount()));
        txtLastOrderDate.setText(String.valueOf(vendor.getLastOrderDate()));
        txtSeasonalDiscDate.setText(String.valueOf(vendor.getSeasonalDiscDate()));
    }

    public JPanel getPanel() {
        return rootPanel;
    }

    /**
     * Invoked to add an ActionListener and a KeyListener to JTextFields, JFormattedTextFields,
     * and an ActionListener to Jbuttons
     *
     */
    private void addListeners() {
        Component[] components = rootPanel.getComponents();

        for (Component component : components) {
            if (component instanceof JTextField)  {
                ((JTextField) component).addActionListener(this);
                component.addKeyListener(this);
            } else if (component instanceof JFormattedTextField) {
                ((JFormattedTextField) component).addActionListener(this);
                component.addKeyListener(this);
            } else if (component instanceof JButton) {
                ((JButton) component).addActionListener(this);
            } else if (component instanceof JComboBox) {
                ((JComboBox) component).addActionListener(this);
            }
        }
    }

    private MaskFormatter formatter(String format, char placeHolder) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(format);
            formatter.setPlaceholderCharacter(placeHolder);
        } catch (ParseException e) {
            // output error message
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
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        if (userAction == txtFullName) {
        } else if (userAction == txtStreetAddress) {
        } else if (userAction == txtCity) {
        } else if (userAction == cboState) {
            state = (StateAbbrs) cboState.getSelectedItem();
        } else if (userAction == txtPhoneNum) {
        } else if (userAction == txtBalance) {
        } else if (userAction == txtLastPaidAmount) {
        } else if (userAction == txtLastOrderDate) {
        } else if (userAction == txtSeasonalDiscDate) {
        } else if (userAction == btnCreate) {
            if (getInputs()) {
                Vendor vendor = new Vendor(name, streetAddress, city, state, phoneNum, balance,
                        lastPaidAmount, lastOrderDate, seasonalDiscDate);
                vendorList.addVendor(vendor);
                displayList.addVendor(vendor);
                mainWindowGUI.setJPanel(new VendorUI().getPanel());
            }
        } else if (userAction == btnCancel) {
            mainWindowGUI.setJPanel(new VendorUI().getPanel());
        }
    }

    private boolean getInputs() {
        String message = " cannot be blank.";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateValidator validator = new DateValidator(dateFormat);

        if (txtFullName.getText().isEmpty()) {
            displayError("Full Name" + message);
            return false;
        } else {
            if (vendorList.searchVendorList(txtFullName.getText()) > -1) {
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
                displayError("Seasonal Discount Start Date");
                return false;
            }
        }

        return true;
    }

    private double getNumber(String numStr) {
        try {
            Number number = numberFormat.parse(numStr);
            return number.doubleValue();
        } catch (ParseException  e) {
            return -1;
        }
    }

    private void displayError(String message)
    {
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
    public void keyPressed(KeyEvent e) { }

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

// add a key listener for max char