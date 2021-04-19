package GUI.PurchaseOrderManagement;

import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;
import GUI.VendorManagement.VendorUI;
import src.Item.Item;
import src.PurchaseOrder.PurchaseOrder;
import src.PurchaseOrder.PurchaseOrderDatabase;
import src.PurchaseOrder.PurchaseOrderDetails;
import src.Vendor.DateValidator;
import src.Vendor.Vendor;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ItemPOInfo implements ActionListener {
    private JPanel rootPanel;
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

    private NumberFormat numberFormat = NumberFormat.getInstance();
    private NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private DateValidator validator = new DateValidator(dateFormat);

    private JPanel pnlPurchaseOrder;
    private PurchaseOrder purchaseOrder;
    private MainWindowGUI mainWindowGUI;
    private Item item;

    public ItemPOInfo(JPanel pnlPurchaseOrder, PurchaseOrder purchaseOrder, Item item) {
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

        txtNeedByDate.setFormatterFactory(new DefaultFormatterFactory(formatter(dateFormat2, '-')));
        txtQuantity.setFormatterFactory(new DefaultFormatterFactory(numberFormatter));
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

    private boolean checkInputs() {
        String message = " cannot be blank";

        if (!txtNeedByDate.isEditValid()) {
            DialogDisplay.displayError("Need by Date" + message);
            return false;
        } else {
            needByDate = validator.getDate(txtNeedByDate.getText());
            if (needByDate == null) {
                DialogDisplay.displayError("Incorrect Last Order Date");
                return false;
            } else if (!validator.isFutureDate(needByDate)) {
                DialogDisplay.displayError("Past Last Order Date");
                return false;
            }
        }

        if (!txtQuantity.getText().isEmpty()) {
            quantity = getNumber(txtQuantity.getText());
            if (quantity <= 0) {
                DialogDisplay.displayError("Quantity must be greater than 0");
                return false;
            }
        } else {
            DialogDisplay.displayError("Quantity" + message);
        }
        return true;
    }

    private double getNumber(String numStr) {
        try {
            Number number = numberFormat.parse(numStr);
            return number.doubleValue();
        } catch (ParseException e) {
            return -1;
        }
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
