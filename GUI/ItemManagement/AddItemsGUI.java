package GUI.ItemManagement;
import GUI.Login.LoginGUI;
import src.Item.ItemsArray;
import src.Item.ItemsValidation;
import GUI.MainWindow.MainWindowGUI;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Random;
import src.Vendor.VendorList;
import src.Vendor.Vendor;

public class AddItemsGUI implements FocusListener {
    private JFrame frame;
    private JPanel rootPanel;

    private JTextField iIDField;
    private JTextField iNameField;
    private JTextField vIDField;
    private JTextField sPriceField;
    private JTextField pPriceField;
    private JTextField quantityField;
    private JButton leaveButton;
    private JButton addItemButton;
    private JComboBox categoryCombo;
    private JComboBox unitCombo;
    private JFormattedTextField expirationFormattedText;
    private JComboBox vendorCombo;
    private JButton logoutButton;
    private JTextField focused = iNameField;

    MainWindowGUI mainWindowGUI;
    VendorList vendorList = VendorList.getInstance();

    NumberFormat nf = new DecimalFormat();

    private static int id = 9999;

    private String[] cat = new String[]{"Vegetables", "Fruits", "Nuts", "Dairy", "Meat", "Snacks", "Soda", "Juice", "Bakery Products"};
    private String[] unit = new String[]{"Pound","Gallon","Dozen"};

    public AddItemsGUI(){
        mainWindowGUI = MainWindowGUI.getInstance();
        setupGUI();
    }

    private void setupGUI()
    {

        expirationFormattedText.setFormatterFactory(new DefaultFormatterFactory(format("##/##/####")));
        Random r = new Random();
        id+= r.nextInt(10)+1;
        iIDField.setText(String.valueOf(id));
        iIDField.addFocusListener(this);
        iNameField.addFocusListener(this);

        sPriceField.addFocusListener(this);
        expirationFormattedText.addFocusListener(this);
        pPriceField.addFocusListener(this);
        quantityField.addFocusListener(this);

        DefaultComboBoxModel<String> catModel = new DefaultComboBoxModel<>(cat);
        categoryCombo.setModel(catModel);

        DefaultComboBoxModel<String> unitModel = new DefaultComboBoxModel<>(unit);
        unitCombo.setModel(unitModel);

        vendorCombo.setModel(new DefaultComboBoxModel(vendorList.getIdList()));

        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeItemEdit();
            }});
        addItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ItemsValidation val = new ItemsValidation();
                try {
                    if(val.validation(id,iNameField.getText(), (int) vendorCombo.getSelectedItem(),Double.parseDouble(sPriceField.getText()),expirationFormattedText.getText(),
                            Double.parseDouble(pPriceField.getText()),Integer.parseInt(quantityField.getText()))){
                        new ItemsArray(id, (int) vendorCombo.getSelectedItem(), iNameField.getText(), Double.parseDouble(sPriceField.getText()), (String) categoryCombo.getSelectedItem(),
                                expirationFormattedText.getText(), Double.parseDouble(pPriceField.getText()), (String) unitCombo.getSelectedItem(), Integer.parseInt(quantityField.getText()));
                    }
                }
                catch(NumberFormatException n){
                    JOptionPane.showMessageDialog(null, "Make sure you have entered correct numerical values");
                }
                catch(NullPointerException N){
                    JOptionPane.showMessageDialog(null, "Please make sure you have chosen a Vendor ID");
                }

                closeItemEdit();

            }});
        logoutButton.addActionListener(e ->
        {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        });
    }

    public void closeItemEdit(){
        mainWindowGUI.setJPanel(new ItemsGUI().getPanel());
    }

    public void focusGained(FocusEvent e) {
        if (e.getSource() instanceof JTextField) {
            focused = (JTextField) e.getSource();
        }
    }
    public void focusLost(FocusEvent e) {
    }
    private MaskFormatter format(String f) {
        MaskFormatter format = null;
        try { format = new MaskFormatter(f);
        } catch (java.text.ParseException e) {
            JOptionPane.showMessageDialog(null, "Please Enter a Valid format"); }
        return format;
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
