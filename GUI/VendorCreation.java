package GUI;

import Application.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Vector;

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

    public VendorCreation() {
        //mainFrame = new JFrame("Application.Vendor Application.Profile Creation");
        String phoneFormat = "###-###-####";
        String dateFormat = "##/##/####";
        addListeners();
        NumberFormatter numberFormatter = new NumberFormatter(NumberFormat.getInstance());

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
        txtBalance.setValue(vendor.getBalance());
        txtLastPaidAmount.setText(String.valueOf(vendor.getLastPaidAmount()));
        txtLastOrderDate.setText(String.valueOf(vendor.getLastOrderDate()));
        txtSeasonalDiscDate.setText(String.valueOf(vendor.getSeasonalDiscDate()));
    }

    public JPanel getPanel() {
        return rootPanel;
    }

    public void createVendor() {
        //createWindow(400, 400);
    }

    private void createWindow(int w, int h) {
        //mainFrame.setContentPane(new VendorCreation().rootPanel);
        //mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //mainFrame.setPreferredSize(new Dimension(w, h));
        //mainFrame.setVisible(true);
        //mainFrame.setResizable(false);

        //mainFrame.pack();

        //txtFullName.requestFocusInWindow();

        //placeWindow();
    }

    /*private void placeWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - mainFrame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - mainFrame.getHeight()) / 2);
        mainFrame.setLocation(x, y);
    }*/

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
            // check if vendor name is used already
            name = txtFullName.getText();
            System.out.println(name);
        } else if (userAction == txtStreetAddress) {
            streetAddress = txtStreetAddress.getText();
        } else if (userAction == txtCity) {
            city = txtCity.getText();
        } else if (userAction == cboState) {
            state = (StateAbbrs) cboState.getSelectedItem();
        } else if (userAction == txtPhoneNum) {
            phoneNum = txtPhoneNum.getText();
        } else if (userAction == txtBalance) {
            balance = Double.parseDouble(txtBalance.getText());
            System.out.println(balance);
        } else if (userAction == txtLastPaidAmount) {
            lastPaidAmount = Double.parseDouble(txtLastPaidAmount.getText());
        } else if (userAction == txtLastOrderDate) {
            //lastOrderDate = txtLastOrderDate.getText();
        } else if (userAction == txtSeasonalDiscDate) {
           // seasonalDiscDate = txtSeasonalDiscDate.getText();
        } else if (userAction == btnCreate) {
            if (getInputs()) {
                Vendor vendor = new Vendor(name, streetAddress, city, state, phoneNum, balance,
                        lastPaidAmount, lastOrderDate, seasonalDiscDate);
                vendorList.addVendor(vendor);
                displayList.addVendor(vendor);
                SwingUtilities.getWindowAncestor(rootPanel).dispose();
            }
        } else if (userAction == btnCancel) {
            SwingUtilities.getWindowAncestor(rootPanel).dispose();
        }
    }

    private boolean getInputs() {
        String message = " cannot be blank.";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateValidator validator = new DateValidator(dateFormat);

        System.out.println(txtPhoneNum.getText());
        System.out.println(txtPhoneNum.getValue());

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
            balance = Double.parseDouble(txtBalance.getText());
        } else {
            balance = 0;
        }
        if (!txtLastPaidAmount.getText().isEmpty()) {
            lastPaidAmount = Double.parseDouble(txtLastPaidAmount.getText());
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