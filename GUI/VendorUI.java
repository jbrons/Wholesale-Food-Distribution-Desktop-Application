package GUI;

import javax.swing.*;

import Application.StateAbbrs;
import Application.Vendor;
import Application.VendorList;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * This class implements a user interface for Purchaser or Owner users for the CSC 4110 Project
 *
 * @author Jordan Bronstetter
 * @date 3/07/2021
 */
public class VendorUI implements ActionListener {
    private JFrame mainFrame;
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

    private VendorCreation vendorCreation = new VendorCreation();
    VendorList vendorList = VendorList.getInstance();
    DisplayList displayList = DisplayList.getInstance();

    public VendorUI() {
        mainFrame = new JFrame("Vendor Profile Hub");

        addListeners();
        lstDisplay.setModel(displayList.getDisplayListModel());
        lstDisplay.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        // ask Jacob
        /*if ( EnumUserRoles.PURCHASER) {
           btnViewProfiles.setEnabled(false);
           btnViewProfiles.setVisible(false);
        }*/
    }

    public static void main(String[] args) {
        VendorUI vendorUI = new VendorUI();
        vendorUI.createWindow(500, 350);
    }

    private void createWindow(int w, int h) {
        mainFrame.setContentPane(new VendorUI().rootPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //mainFrame.setPreferredSize(new Dimension(w, h));
        mainFrame.setVisible(true);
        //mainFrame.setResizable(false);

        mainFrame.pack();

        txtSearchBar.requestFocusInWindow();

        placeWindow();
    }

    private void placeWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - mainFrame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - mainFrame.getHeight()) / 2);
        mainFrame.setLocation(x, y);
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
            setJPanel(new VendorCreation().getPanel());
            //vendorCreation.createVendor();

        } else if (userAction == btnUpdate) {
            vendor = lstDisplay.getSelectedValue();
           if (vendor == null) {
               displayError("Please select a Vendor to update");
           } else {
               // call vendor update
           }
        } else if (userAction == btnDelete) {
            vendor = lstDisplay.getSelectedValue();
            if (vendor == null) {
                displayError("Please select a Vendor to update");
            } else {

            }
        } else if (userAction == btnViewProfiles) {

        }
        //MainWindowGUI mainWindowGUI = MainWindowGUI.getInstance();
        //mainWindowGUI.setJPanel(new LoginGUI().getPanel()));
    }

    private void displayError(String message)
    {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
    }


    /**
     *  DELETE
     */
    public void setJPanel(JPanel newPanel)
    {
        mainFrame.setContentPane(newPanel);
        mainFrame.setVisible(true);
        mainFrame.pack();
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

        txtSearchBar.addActionListener(this);
    }
}