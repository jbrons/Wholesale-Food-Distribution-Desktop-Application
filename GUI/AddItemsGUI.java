package GUI;

import Item.ItemsArray;
import Item.ItemsValidation;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;


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
    private JTextField focused = iNameField;


    NumberFormat nf = new DecimalFormat();

    private static int id = 99999;

    private String[] cat = new String[]{"Vegetables", "Fruits", "Nuts", "Dairy", "Meat", "Snacks", "Soda", "Juice", "Bakery Products"};
    private String[] unit = new String[]{"Pound","Gallon","Dozen"};




    public AddItemsGUI(){
        frame = new JFrame("Add Items");
        frame.setTitle("Add Items");
        expirationFormattedText.setFormatterFactory(new DefaultFormatterFactory(format("##/##/####")));
        Random r = new Random();
        id+= r.nextInt(10)+1;
        iIDField.setText(String.valueOf(id));
        iIDField.addFocusListener(this);
        iNameField.addFocusListener(this);
        vIDField.addFocusListener(this);
        sPriceField.addFocusListener(this);
        expirationFormattedText.addFocusListener(this);
        pPriceField.addFocusListener(this);
        quantityField.addFocusListener(this);

        DefaultComboBoxModel<String> catModel = new DefaultComboBoxModel<>(cat);
        categoryCombo.setModel(catModel);

        DefaultComboBoxModel<String> unitModel = new DefaultComboBoxModel<>(unit);
        unitCombo.setModel(unitModel);
        /*categoryCombo.setModel(new DefaultComboBoxModel<>(CategoryAbbrs.Category.values()));
        unitCombo.setModel(new DefaultComboBoxModel<>(CategoryAbbrs.Category.toString()));
*/




        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeItemEdit();
            }});
        addItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ItemsValidation val = new ItemsValidation();
                try {
                    if(val.validation(id,iNameField.getText(),Integer.parseInt(vIDField.getText()),Double.parseDouble(sPriceField.getText()),expirationFormattedText.getText(),
                            Double.parseDouble(pPriceField.getText()),Integer.parseInt(quantityField.getText()))){
                        new ItemsArray(id, Integer.parseInt(vIDField.getText()), iNameField.getText(), Double.parseDouble(sPriceField.getText()), (String) categoryCombo.getSelectedItem(),
                                expirationFormattedText.getText(), Double.parseDouble(pPriceField.getText()), (String) unitCombo.getSelectedItem(), Integer.parseInt(quantityField.getText()));
                    }
                }
                catch(NumberFormatException n){
                    JOptionPane.showMessageDialog(null, "Make sure you have entered correct numerical values");
                }
                closeItemEdit();

            }});


    }
    public void displayAddItem() {

        frame.setContentPane(new AddItemsGUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();

    }

    public void closeItemEdit(){
        SwingUtilities.getWindowAncestor(rootPanel).setVisible(false);
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
}
