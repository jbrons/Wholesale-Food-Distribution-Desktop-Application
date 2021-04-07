/*
 * Name:Zachary Nicolai
 * Class Name: EditItemsGUI
 * Class Description: This class controls the EditItemsGUI. it gives instructions for all of the buttons text fields, and
 * combo boxes. The GUI allows users to edit current Item profiles. It will show information about selected item when editing.
 */


package GUI.ItemManagement;

import GUI.Login.LoginGUI;
import src.Item.Items;
import src.Item.ItemsArray;
import src.Item.ItemsValidation;
import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.event.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import src.User.UserDatabase;
import src.Vendor.VendorList;
import GUI.MainWindow.MainWindowGUI;


public class EditItemsGUI implements FocusListener {
    private JPanel rootPanel;

    private JTextField iIDField;
    private JTextField iNameField;
    private JTextField quantityField;
    private JButton leaveButton;
    private JButton editItemButton;
    private JComboBox categoryCombo;
    private JComboBox unitCombo;
    private JFormattedTextField expFormattedText;
    private JComboBox vendorCombo;
    private JButton logoutButton;
    private JFormattedTextField sPriceFormattedText;
    private JFormattedTextField pPriceFormattedText;
    private int index;
    private ArrayList<Items> itemsListCopy;
    private JTextField focused = iNameField;

    private String[] cat = new String[]{"Vegetables", "Fruits", "Nuts", "Dairy", "Meat", "Snacks", "Soda", "Juice", "Bakery Products"};
    private String[] unit = new String[]{"Pound","Gallon","Dozen","Ounce","Per Unit"};
    UserDatabase dataBase = UserDatabase.getInstance();
    MainWindowGUI mainWindowGUI;
    VendorList vendorList = VendorList.getInstance();

    public EditItemsGUI(int i) {
        mainWindowGUI = MainWindowGUI.getInstance();
        this.setIndex(i);
        setupGUI();
    }

    public void setupGUI()
    {
        //setting combo boxes
        DefaultComboBoxModel<String> catModel = new DefaultComboBoxModel<>(cat);
        categoryCombo.setModel(catModel);
        DefaultComboBoxModel<String> unitModel = new DefaultComboBoxModel<>(unit);
        unitCombo.setModel(unitModel);
        //putting current vendor ids in combo box
        vendorCombo.setModel(new DefaultComboBoxModel(vendorList.getIdList()));

        expFormattedText.setFormatterFactory(new DefaultFormatterFactory(format("##/##/####")));
        sPriceFormattedText.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getInstance())));
        pPriceFormattedText.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getInstance())));

        //setting focus listeners
        iIDField.addFocusListener(this);
        iNameField.addFocusListener(this);
        sPriceFormattedText.addFocusListener(this);
        expFormattedText.addFocusListener(this);
        pPriceFormattedText.addFocusListener(this);
        quantityField.addFocusListener(this);

        //setting text fields based on current item profile details
        DecimalFormat df = new DecimalFormat("#.00");
        //getting current itemsList
        itemsListCopy = ItemsArray.getItemsList();
        iIDField.setText(String.valueOf(itemsListCopy.get(this.index).getId()));
        iNameField.setText(itemsListCopy.get(this.index).getName());
        vendorCombo.setSelectedItem(itemsListCopy.get(this.index).getVendorId());
        sPriceFormattedText.setText(String.valueOf(df.format(itemsListCopy.get(this.index).getSellingPrice())));
        categoryCombo.setSelectedItem(itemsListCopy.get(this.index).getCategory());
        expFormattedText.setText(itemsListCopy.get(this.index).getExpirationDate());
        pPriceFormattedText.setText(String.valueOf(df.format(itemsListCopy.get(this.index).getPurchasePrice())));
        unitCombo.setSelectedItem(itemsListCopy.get(this.index).getUnit());
        quantityField.setText(String.valueOf(itemsListCopy.get(this.index).getQuantity()));

        //button actions
        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeItemEdit();
            }
        });
        editItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                ItemsValidation val = new ItemsValidation();
                //try block ensures the user doesn't enter an invalid number for numerical inputs
                try {
                    //validation method ensures all other inputs arent invalid
                    if(val.validation(Integer.parseInt(iIDField.getText()),iNameField.getText(),(int)vendorCombo.getSelectedItem(),
                            Double.parseDouble(sPriceFormattedText.getText().replace(",","")), expFormattedText.getText(),
                            Double.parseDouble(pPriceFormattedText.getText().replace(",","")),Integer.parseInt(quantityField.getText()))) {
                        //setting item information
                        ItemsArray.setIndexID(getIndex(), Integer.parseInt(iIDField.getText()));
                        ItemsArray.setIndexName(getIndex(), iNameField.getText());
                        ItemsArray.setIndexVId(getIndex(), (int)vendorCombo.getSelectedItem());
                        ItemsArray.setIndexPPrice(getIndex(), Double.parseDouble(pPriceFormattedText.getText().replace(",","")));
                        ItemsArray.setIndexCategory(getIndex(), (String) categoryCombo.getSelectedItem());
                        ItemsArray.setIndexExpiration(getIndex(), expFormattedText.getText());
                        ItemsArray.setIndexSPrice(getIndex(), Double.parseDouble(sPriceFormattedText.getText().replace(",","")));
                        ItemsArray.setIndexUnit(getIndex(), (String) unitCombo.getSelectedItem());
                        ItemsArray.setIndexID(getIndex(), Integer.parseInt(iIDField.getText()));
                        ItemsArray.setIndexQuantity(getIndex(),Integer.parseInt(quantityField.getText()));
                        closeItemEdit();
                    }
                }
                catch(NumberFormatException n){
                    JOptionPane.showMessageDialog(null, "Make sure you have entered correct numerical values");}
                catch(NullPointerException N){
                    JOptionPane.showMessageDialog(null, "Please make sure you have chosen a Vendor ID");
                }
            }




        });
        logoutButton.addActionListener(e ->
        {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        });
    }

    public void closeItemEdit(){
        mainWindowGUI.setJPanel(new ItemDisplayGUI(index).getPanel());
    }

    public int getIndex(){
        return this.index;
    }
    public void setIndex(int i){
        this.index = i;
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







