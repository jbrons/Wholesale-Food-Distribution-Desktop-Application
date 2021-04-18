package GUI.PurchaseOrderManagement;

import GUI.MainWindow.MainWindowGUI;
import src.Item.ItemsDatabase;
import src.User.EnumUserRoles;
import src.Vendor.VendorList;

import javax.swing.*;
import java.awt.event.*;

public class PurchaseOrderGUI implements ActionListener, MouseListener, FocusListener {
    private JList lstItems;
    private JPanel rootPanel;
    private JTextField txtSearchBar;
    private JButton btnSelectVendor;
    private JTextField txtInstructions;
    private JLabel lblSearch;
    private JButton btnMainMenu;
    private JButton btnLogOut;
    private JButton btnCreatePO;
    private JButton btnCancelPO;
    private JButton btnViewPO;

    VendorList vendorDatabase = VendorList.getInstance();
    ItemsDatabase itemsDatabase = ItemsDatabase.getInstance();
    MainWindowGUI mainWindowGUI;

    public PurchaseOrderGUI() {
        mainWindowGUI = MainWindowGUI.getInstance();
        mainWindowGUI.setTitle("Purchase Order Management");

        setUpGUI();
    }

    private void setUpGUI() {
        addListeners();

        txtSearchBar.requestFocusInWindow();
        txtSearchBar.setText("Search by Vendor Name");

        lstItems.setListData(itemsList.getAllItemDetails());
    }

    /**
     * Invoked to add a ActionListener to JButtons and JTextFields
     */
    private void addListeners() {
        txtSearchBar.addFocusListener(this);
        lstItems.addMouseListener(this);
        btnCreatePO.addActionListener(this);
        btnCancelPO.addActionListener(this);
        btnViewPO.addActionListener(this);
        btnMainMenu.addActionListener(this);
        btnLogOut.addActionListener(this);
        btnSelectVendor.addActionListener(this);
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object userAction = e.getSource();

        if (userAction == btnSelectVendor) {
            String input = txtSearchBar.getText();
            int index = vendorList.getIndex(input);
                    lstDisplay.setSelectedIndex(index);

        } else if (userAction == btnCreatePO) {

        } else if (userAction == btnCancelPO) {

        } else if (userAction == btnViewPO) {

        } else if (userAction == btnMainMenu) {

        } else if (userAction == btnLogOut) {

        }
    }
}
