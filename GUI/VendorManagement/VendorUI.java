package GUI.VendorManagement;

import javax.swing.*;

import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import src.User.EnumUserRoles;
import src.User.UserDatabase;
import src.Vendor.StateAbbrs;
import src.Vendor.Vendor;
import src.Vendor.VendorList;

import GUI.MainWindow.MainWindowGUI;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * This class implements a user interface for Purchaser or Owner users for the CSC 4110 Project
 *
 * @author Jordan Bronstetter
 * @date 3/07/2021
 */
public class VendorUI implements ActionListener {
    private JPanel rootPanel;

    private JLabel lblSearch;
    private JTextField txtSearchBar;

    private JButton btnGo;
    private JButton btnViewProfiles;

    private JTextArea txtDisplayInfo;
    private JLabel lblManageProfiles;
    private JButton btnCreate;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JList<Vendor> lstDisplay;
    private JButton btnLogOut;
    private JButton btnMainMenu;

    VendorList vendorList = VendorList.getInstance();
    DisplayList displayList = DisplayList.getInstance();
    UserDatabase database = UserDatabase.getInstance();

    MainWindowGUI mainWindowGUI;

    public VendorUI() {
        mainWindowGUI = MainWindowGUI.getInstance();
        addListeners();
        lstDisplay.setModel(displayList.getDisplayListModel());
        lstDisplay.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        txtSearchBar.requestFocusInWindow();

        if (database.getCurrentUser().getRole() == EnumUserRoles.PURCHASER) {
           btnViewProfiles.setEnabled(false);
           btnViewProfiles.setVisible(false);
        }
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int maxChars = 20;
        int idLength = 6;
        Vendor vendor = null;

        Object userAction = e.getSource();

        if (userAction == txtSearchBar) {
            String input = txtSearchBar.getText();
            if (input.length() > maxChars) {
                // give error message
            } else if (input.length() <= idLength) {
                try {
                    int vendorId = Integer.parseInt(input);
                    // it is an id
                } catch (NumberFormatException ex) {
                    // it is a name
                }
            }

        } else if (userAction == btnCreate) {
            mainWindowGUI.setJPanel(new VendorCreation().getPanel());
        } else if (userAction == btnUpdate) {
            vendor = lstDisplay.getSelectedValue();
           if (vendor == null) {
               displayError("Please search for/select a Vendor to update");
           } else {
               mainWindowGUI.setJPanel(new VendorCreation(vendor).getPanel());
           }
        } else if (userAction == btnDelete) {
            vendor = lstDisplay.getSelectedValue();
            if (vendor == null) {
                displayError("Please select a Vendor to delete");
            } else {
                displayList.removeVendor(vendor);
                vendorList.deleteVendor(vendor);
            }
        } else if (userAction == btnViewProfiles) {

        } else if (userAction == btnLogOut) {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        } else if (userAction == btnMainMenu) {
            mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());
        }
    }

    private void displayError(String message)
    {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
    }


    public JPanel getPanel()
    {
        return rootPanel;
    }

    /**
     * Invoked to add a ActionListener to JButtons and JTextFields
     */
    private void addListeners() {
        Component[] components = rootPanel.getComponents();

        btnCreate.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnViewProfiles.addActionListener(this);
        btnLogOut.addActionListener(this);
        txtSearchBar.addActionListener(this);
        btnMainMenu.addActionListener(this);
    }
}