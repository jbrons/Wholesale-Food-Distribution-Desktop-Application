package GUI.VendorManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import GUI.PurchaseOrderManagement.DialogDisplay;
import src.User.EnumUserRoles;
import src.User.UserDatabase;
import src.Vendor.VendorDatabase;
import GUI.MainWindow.MainWindowGUI;

/**
 * VendorHubGUI implements the main GUI page for Purchaser or Owner users for the CSC 4110 Project.
 * It displays the list of Vendors differently, based upon user permissions:
 *  Purchaser users can see the search results of one Vendor at a time, which they can select to update or delete
 *  Owner users can search from and see a list of vendors and select one to update or delete
 *
 * @author Jordan Bronstetter
 * @date 3/07/2021
 *
 */
public class VendorHubGUI implements ActionListener {
    private JPanel rootPanel;
    private JPanel pnlListBackground;

    private JLabel lblSearch;
    private JLabel lblSearchResults;
    private JLabel lblListInfo;

    private JTextField txtSearchBar;

    private JButton btnGo;
    private JButton btnViewProfiles;
    private JButton btnCreateProfile;
    private JButton btnUpdateProfile;
    private JButton btnDeleteProfile;
    private JButton btnLogOut;
    private JButton btnMainMenu;

    private JScrollPane scpDisplay;
    private JList<String> lstDisplay;

    private static boolean viewProfiles = true;
    private boolean listActive = true;
    private int searchIndex = -1;
    private EnumUserRoles role;

    private static VendorDatabase vendordatabase = VendorDatabase.getInstance();
    private static VendorModel vendorModel = VendorModel.getInstance();
    MainWindowGUI mainWindowGUI;

    public VendorHubGUI() {
        mainWindowGUI = MainWindowGUI.getInstance();
        mainWindowGUI.setTitle("Vendor Management");
        role = UserDatabase.getInstance().getCurrentUser().getRole();

        setUpGUI();
        addListeners();
    }

    private void setUpGUI() {
        btnGo.setEnabled(false);
        txtSearchBar.requestFocusInWindow();

       if (role == EnumUserRoles.PURCHASER) {
           lblSearchResults.setVisible(true);
           lblSearchResults.setEnabled(true);
           btnViewProfiles.setEnabled(false);
           btnViewProfiles.setVisible(false);
           scpDisplay.setEnabled(false);
           scpDisplay.setVisible(false);
           listActive = false;
       } else {
           lstDisplay.setModel(vendorModel.getDisplayListModel());
           lstDisplay.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
           viewProfiles = !viewProfiles;
           displayList();
       }
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int index = -1;
        Object userAction = e.getSource();

        if (userAction == btnGo) {
            if (vendordatabase.isEmpty()) {
                DialogDisplay.displayError("No Vendors to view");
                return;
            }
            String input = txtSearchBar.getText();
            index = VendorHub.getSearchResults(input);
            if (index > -1) {
                displaySelectVendor(index);
            } else {
                resetSearchResults();
            }
            txtSearchBar.setText(null);
            btnGo.setEnabled(false);
        } else if (userAction == btnViewProfiles) {
            if (viewProfiles && vendordatabase.isEmpty()) {
                DialogDisplay.displayError("No Vendors to view");
                resetSearchResults();
            } else {
                displayList();
            }
        } else if (userAction == btnCreateProfile) {
            mainWindowGUI.setJPanel(new VendorCreationGUI().getPanel());
        } else if (userAction == btnUpdateProfile) {
            index = setIndex();
            if (index < 0) {
                DialogDisplay.displayError("Please select a Vendor to update");
            } else {
                mainWindowGUI.setJPanel(new VendorCreationGUI(vendordatabase.getVendor(index)).getPanel());
            }
        } else if (userAction == btnDeleteProfile) {
            index = setIndex();
            if (VendorHub.deleteVendor(index)) {
                resetSearchResults();
                displayList();
            }
        } else if (userAction == btnLogOut) {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        } else if (userAction == btnMainMenu) {
            mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());
        }
    }

    private void resetSearchResults() {
        if (role == EnumUserRoles.PURCHASER) {
            lblSearchResults.setText(null);
            lblSearchResults.setBackground(Color.white);
        }
    }

    private void displaySelectVendor(int index) {
        if (role == EnumUserRoles.OWNER) {
            if (viewProfiles) {
                displayList();
            }
            lstDisplay.setSelectedIndex(index);
        } else {
            lblSearchResults.setText(vendordatabase.getVendorDetails(index));
            lblSearchResults.setBackground(Color.lightGray);
            searchIndex = index;
        }
    }

    private void displayList() {
        String hideMessage = "Hide Profiles";
        String viewMessage = "View Profiles";

        if (viewProfiles) {
            btnViewProfiles.setText(hideMessage);
        } else {
            btnViewProfiles.setText(viewMessage);
        }
        scpDisplay.setVisible(viewProfiles);
        lstDisplay.setVisible(viewProfiles);
        viewProfiles = !viewProfiles;
        lstDisplay.clearSelection();
    }

    private int setIndex() {
        int index = -1;
        if (listActive) {
            index = lstDisplay.getSelectedIndex();
        } else {
            index = searchIndex;
        }
        return index;
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }

    /**
     * Invoked to add a ActionListener to JButtons and JTextFields
     */
    private void addListeners() {
        btnCreateProfile.addActionListener(this);
        btnUpdateProfile.addActionListener(this);
        btnDeleteProfile.addActionListener(this);
        btnViewProfiles.addActionListener(this);
        btnLogOut.addActionListener(this);
        txtSearchBar.addActionListener(this);
        btnMainMenu.addActionListener(this);
        btnGo.addActionListener(this);

        /**
         * Invoked when a key has been typed.
         * See the class description for {@link KeyEvent} for a definition of
         * a key typed event.
         *
         * @param e the event to be processed
         */

        txtSearchBar.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                int maxChars = 20;
                int length = txtSearchBar.getText().length();
                if (length >= maxChars) {
                    e.consume();
                }
            }

            /**
             * Invoked when a key has been pressed.
             * See the class description for {@link KeyEvent} for a definition of
             * a key pressed event.
             *
             * @param e the event to be processed
             */
            @Override
            public void keyPressed(KeyEvent e) {}

            /**
             * Invoked when a key has been released.
             * See the class description for {@link KeyEvent} for a definition of
             * a key released event.
             *
             * @param e the event to be processed
             */
            @Override
            public void keyReleased(KeyEvent e) {
                int length = txtSearchBar.getText().length();
                if (length > 0) {
                    btnGo.setEnabled(true);
                } else {
                    btnGo.setEnabled(false);
                }
            }
        });
    }
}