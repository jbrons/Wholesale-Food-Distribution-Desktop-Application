package GUI.PurchaseOrderManagement;

import GUI.MainWindow.MainWindowGUI;
import src.Vendor.ParseStrings;
import src.Item.Item;
import src.PurchaseOrder.PurchaseOrder;
import src.PurchaseOrder.PurchaseOrderDetails;
import src.Vendor.DateValidator;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * ItemPOInfo implements a GUI page  for purchase users to add the Purchase Order Details to a Purchase Order
 *
 * @author Jordan Bronstetter
 * @date 04/09/2021
 *
 */
public class ItemPOInfoGUI implements ActionListener {
    private JPanel rootPanel;
    private JPanel pnlPurchaseOrder;

    private JFormattedTextField txtNeedByDate;
    private JFormattedTextField txtQuantity;
    private JLabel lblNeedByDate;
    private JLabel lblQuantity;
    private JButton btnAdd;
    private JButton btnCancel;
    private JList lstPurchaseOrders;

    private LocalDate needByDate;
    private double quantity;
    private int vendorId;

    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private DateValidator validator = new DateValidator(dateFormat);
    private PurchaseOrder purchaseOrder;
    private Item item;
    private MainWindowGUI mainWindowGUI;

    public ItemPOInfoGUI(JPanel pnlPurchaseOrder, PurchaseOrder purchaseOrder, Item item) {
        mainWindowGUI = MainWindowGUI.getInstance();
        mainWindowGUI.setTitle("Purchase Order Management");

        this.pnlPurchaseOrder = pnlPurchaseOrder;
        this.item = item;
        vendorId = item.getVendorId();
        this.purchaseOrder = purchaseOrder;

        setUpGUI();
        addListeners();
    }

    private void setUpGUI() {
        String dateFormat2 = "##/##/####";

        txtNeedByDate.setFormatterFactory(
                new DefaultFormatterFactory(ParseStrings.formatter(dateFormat2, '-')));
        txtQuantity.setFormatterFactory(new DefaultFormatterFactory(ParseStrings.getNumberFormatter()));
    }

    /**
     * Checks if all the inputs are valid and returns false at the first invalid input
     *
     * @return false if there are invalid inputs, otherwise true
     * */
    private boolean checkInputs() {
        String message = " cannot be blank";

        if (!txtNeedByDate.isEditValid()) {
            DialogDisplay.displayError("Need by Date" + message);
            return false;
        } else {
            needByDate = validator.getDate(txtNeedByDate.getText());
            if (needByDate == null) {
                DialogDisplay.displayError("Incorrect Need by Date");
                return false;
            } else if (validator.isPastDate(needByDate)) {
                DialogDisplay.displayError("Need by Date cannot be a past date");
                return false;
            }
        }

        if (!txtQuantity.getText().isEmpty()) {
            quantity = ParseStrings.getNumber(txtQuantity.getText());
            if (quantity <= 0) {
                DialogDisplay.displayError("Quantity must be greater than 0");
                return false;
            }
        } else {
            DialogDisplay.displayError("Quantity" + message);
            return false;
        }
        return true;
    }

    private void addListeners() {
        btnAdd.addActionListener(this);
        btnCancel.addActionListener(this);

        txtQuantity.addKeyListener(new KeyAdapter() {
            /**
             * Invoked when a key has been typed.
             * This event occurs when a key press is followed by a key release.
             *
             * @param e
             */
            @Override
            public void keyTyped(KeyEvent e) {
                String text = txtQuantity.getText();
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
        });
    }

    public JPanel getPanel() {
        return rootPanel;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object userAction = e.getSource();

        if (userAction == btnAdd) {
            if (checkInputs()) {
                PurchaseOrderDetails details = new PurchaseOrderDetails(item.getPurchasePrice(), needByDate, quantity);
                purchaseOrder.addItem(details, item);
                closeGUI();
            }
        } else {
            closeGUI();
        }
    }

    private void closeGUI() {
        mainWindowGUI.setJPanel(pnlPurchaseOrder);
    }
}
