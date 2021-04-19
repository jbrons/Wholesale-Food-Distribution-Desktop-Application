package GUI.ItemManagement;

/**
 * Name:Zachary Nicolai
 * Class Name: AddItemsGUI
 * Class Description: This class controls the AddItemsGUI. it gives instructions for all of the buttons text fields, and
 * combo boxes. The GUI allows users to add new Item profiles.
 *
 * @author Zachary Nicolai
 * @date 03/15/2021
 */

import GUI.Login.LoginGUI;
import src.Item.Item;
import src.Item.ItemsDatabase;
import src.Item.ItemsValidation;
import GUI.MainWindow.MainWindowGUI;
import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Random;
import src.Vendor.VendorDatabase;


public class AddItemsGUI implements FocusListener {
    private JPanel rootPanel;

    private JTextField iIDField;
    private JTextField iNameField;
    private JTextField quantityField;
    private JButton leaveButton;
    private JButton addItemButton;
    private JComboBox categoryCombo;
    private JComboBox unitCombo;
    private JFormattedTextField expirationFormattedText;
    private JComboBox vendorCombo;
    private JButton logoutButton;
    private JFormattedTextField sPriceFormattedText;
    private JFormattedTextField pPriceFormattedText;
    private JTextField focused = iNameField;
    MainWindowGUI mainWindowGUI;
    //Item variables

    //getting the current vendor list and item list
    ItemsDatabase itemsList = ItemsDatabase.getInstance();
    VendorDatabase vendorDatabase = VendorDatabase.getInstance();
    NumberFormat nf = new DecimalFormat();

    private static int id = 9999;

    private String[] cat = new String[]{"Vegetables", "Fruits", "Nuts", "Dairy", "Meat", "Snacks", "Soda", "Juice", "Bakery Products"};
    private String[] unit = new String[]{"Pound","Gallon","Dozen","Ounce", "Per Unit"};

    public AddItemsGUI(){
        mainWindowGUI = MainWindowGUI.getInstance();
        setupGUI();
    }

    private void setupGUI()
    {
        expirationFormattedText.setFormatterFactory(new DefaultFormatterFactory(format("##/##/####")));
        sPriceFormattedText.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getInstance())));
        pPriceFormattedText.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getInstance())));
        Random r = new Random();

        //setting focus listeners
        id+= r.nextInt(10)+1;
        iIDField.setText(String.valueOf(id));
        iIDField.addFocusListener(this);
        iNameField.addFocusListener(this);
        sPriceFormattedText.addFocusListener(this);
        expirationFormattedText.addFocusListener(this);
        pPriceFormattedText.addFocusListener(this);
        quantityField.addFocusListener(this);

        //setting combo boxes
        DefaultComboBoxModel<String> catModel = new DefaultComboBoxModel<>(cat);
        categoryCombo.setModel(catModel);
        DefaultComboBoxModel<String> unitModel = new DefaultComboBoxModel<>(unit);
        unitCombo.setModel(unitModel);
        //putting current vendor ids in combo box
        vendorCombo.setModel(new DefaultComboBoxModel(vendorDatabase.getIdList()));

        //button action listeners
        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeItemAdd();
            }});
        addItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ItemsValidation val = new ItemsValidation();
                boolean added = false;
                //try ensures no bad numerical input or null pointer
                try {

                    //validation call ensures there is no other types of bad user input
                    if(val.validation(id,iNameField.getText(), (int) vendorCombo.getSelectedItem(),Double.parseDouble(sPriceFormattedText.getText().replace(",","")),
                            expirationFormattedText.getText(), Double.parseDouble(pPriceFormattedText.getText().replace(",","")),Double.parseDouble(quantityField.getText()))){

                        Item item = new Item(id,(int) vendorCombo.getSelectedItem(),iNameField.getText(),Double.parseDouble(sPriceFormattedText.getText().replace(",","")),
                                (String) categoryCombo.getSelectedItem(),expirationFormattedText.getText(),Double.parseDouble(pPriceFormattedText.getText().replace(",","")),
                                (String) unitCombo.getSelectedItem(),Double.parseDouble(quantityField.getText()));
                        itemsList.addItem(item);
                        added = true;
                    }
                }
                catch(NumberFormatException n){
                    JOptionPane.showMessageDialog(null, "Make sure you have entered correct numerical values");
                }
                catch(NullPointerException N){
                    JOptionPane.showMessageDialog(null, "Please make sure you have chosen a Vendor ID");
                }
                if(added){
                closeItemAdd();
            }


            }});
        logoutButton.addActionListener(e ->
        {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        });
    }

    public void closeItemAdd(){
        mainWindowGUI.setJPanel(new ItemsGUI().getPanel(),"Item Profile Management");
    }

    //methods required for implementing focus listener
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
