package GUI.PurchaseOrderManagement;

import GUI.MainWindow.MainWindowGUI;
import src.Item.ItemsArray;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PurchaseOrderGUI implements MouseListener, FocusListener {
    private JList lstItems;
    private JPanel rootPanel;
    private JTextField txtSearchBar;
    private JButton btnGo;
    private JTextField txtInstructions;
    private JLabel lblSearch;
    private JButton btnMainMenu;
    private JButton btnLogOut;
    private JPanel pnlPurchaseOrderInfo;

    ItemsArray itemsList = ItemsArray.getInstance();
    MainWindowGUI mainWindowGUI;

    public PurchaseOrderGUI() {
        mainWindowGUI = MainWindowGUI.getInstance();
        mainWindowGUI.setTitle("Purchase Order Management");
        setUpGUI();
    }

    private void setUpGUI() {
        addListeners();
        lstItems.setListData(itemsList.getAllItemDetails());


        txtSearchBar.setText("Search by Vendor Name...");
    }

    /**
     * Invoked to add a ActionListener to JButtons and JTextFields
     */
    private void addListeners() {
        txtSearchBar.addFocusListener(this);
        lstItems.addMouseListener(this);
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }


    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {}

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {}

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * Invoked when a component gains the keyboard focus.
     *
     * @param e the event to be processed
     */
    @Override
    public void focusGained(FocusEvent e) {
        Object userAction = e.getSource();

        if (userAction == txtSearchBar) {
            txtSearchBar.setText("");
        }
    }

    /**
     * Invoked when a component loses the keyboard focus.
     *
     * @param e the event to be processed
     */
    @Override
    public void focusLost(FocusEvent e) {
        Object userAction = e.getSource();

        if (userAction == txtSearchBar) {
            if (txtSearchBar.getText().equals("")) {
                txtSearchBar.setText("Search by Vendor Name...");
            }
        }
    }
}
