package GUI.ItemManagement;

import src.Item.Items;
import src.Item.ItemsArray;
import src.Item.ItemsValidation;
import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import src.User.UserDatabase;
import src.User.EnumUserRoles;

import GUI.MainWindow.MainWindowGUI;

public class EditItemsGUI implements FocusListener {
    private JFrame frame;
    private JPanel rootPanel;

    private JTextField iIDField;
    private JTextField iNameField;
    private JTextField vIDField;
    private JTextField sPriceField;
    private JTextField pPriceField;
    private JTextField quantityField;
    private JButton leaveButton;
    private JButton editItemButton;
    private JComboBox categoryCombo;
    private JComboBox unitCombo;
    private JFormattedTextField expFormattedText;
    private int index;
    private ArrayList<Items> itemsListCopy;
    private JTextField focused = iNameField;

    private String[] cat = new String[]{"Vegetables", "Fruits", "Nuts", "Dairy", "Meat", "Snacks", "Soda", "Juice", "Bakery Products"};
    private String[] unit = new String[]{"Pound","Gallon","Dozen"};
    UserDatabase dataBase = UserDatabase.getInstance();
    MainWindowGUI mainWindowGUI;

    public EditItemsGUI(int i) {
        mainWindowGUI = MainWindowGUI.getInstance();
        this.setIndex(i);
        setupGUI();
    }

    public void setupGUI()
    {

        DefaultComboBoxModel<String> catModel = new DefaultComboBoxModel<>(cat);
        categoryCombo.setModel(catModel);

        DefaultComboBoxModel<String> unitModel = new DefaultComboBoxModel<>(unit);
        unitCombo.setModel(unitModel);


        expFormattedText.setFormatterFactory(new DefaultFormatterFactory(format("##/##/####")));
        iIDField.addFocusListener(this);
        iNameField.addFocusListener(this);
        vIDField.addFocusListener(this);
        sPriceField.addFocusListener(this);
        expFormattedText.addFocusListener(this);
        pPriceField.addFocusListener(this);
        quantityField.addFocusListener(this);

        itemsListCopy = ItemsArray.getItemsList();
        iIDField.setText(String.valueOf(itemsListCopy.get(this.index).getId()));
        iNameField.setText(itemsListCopy.get(this.index).getName());
        vIDField.setText(String.valueOf(itemsListCopy.get(this.index).getVendorId()));
        sPriceField.setText(String.valueOf(itemsListCopy.get(this.index).getSellingPrice()));
        categoryCombo.setSelectedItem(itemsListCopy.get(this.index).getCategory());
        expFormattedText.setText(itemsListCopy.get(this.index).getExpirationDate());
        pPriceField.setText(String.valueOf(itemsListCopy.get(this.index).getPurchasePrice()));
        unitCombo.setSelectedItem(itemsListCopy.get(this.index).getUnit());
        quantityField.setText(String.valueOf(itemsListCopy.get(this.index).getQuantity()));
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
                    if(val.validation(Integer.parseInt(iIDField.getText()),iNameField.getText(),Integer.parseInt(vIDField.getText()),Double.parseDouble(sPriceField.getText()),
                            expFormattedText.getText(),Double.parseDouble(pPriceField.getText()),Integer.parseInt(quantityField.getText()))) {
                        ItemsArray.setIndexID(getIndex(), Integer.parseInt(iIDField.getText()));
                        ItemsArray.setIndexName(getIndex(), iNameField.getText());
                        ItemsArray.setIndexVId(getIndex(), Integer.parseInt(vIDField.getText()));
                        ItemsArray.setIndexPPrice(getIndex(), Double.parseDouble(pPriceField.getText()));
                        ItemsArray.setIndexCategory(getIndex(), (String) categoryCombo.getSelectedItem());
                        ItemsArray.setIndexExpiration(getIndex(), expFormattedText.getText());
                        ItemsArray.setIndexSPrice(getIndex(), Double.parseDouble(sPriceField.getText()));
                        ItemsArray.setIndexUnit(getIndex(), (String) unitCombo.getSelectedItem());
                        ItemsArray.setIndexID(getIndex(), Integer.parseInt(iIDField.getText()));
                        ItemsArray.setIndexQuantity(getIndex(),Integer.parseInt(quantityField.getText()));
                        closeItemEdit();
                    }
                }
                catch(NumberFormatException n){
                    JOptionPane.showMessageDialog(null, "Make sure you have entered correct numerical values");}
            }



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







