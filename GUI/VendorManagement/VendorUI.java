package GUI.VendorManagement;

import javax.swing.*;
import java.awt.event.*;

import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import src.User.EnumUserRoles;
import src.User.UserDatabase;
import src.Vendor.VendorList;
import GUI.MainWindow.MainWindowGUI;

/**
 * This class implements the main user interface for Purchaser or Owner users for the CSC 4110 Project.
 * It displays the list of Vendors differently, based upon user permissions:
 *  Purchaser users can see the search results of one Vendor at a time, which they can select to update or delete
 *  Owner users can search from and see a list of vendors and select one to update or delete
 *
 * @author Jordan Bronstetter
 * @date 3/07/2021
 *
 */
public class VendorUI implements ActionListener {
    private JPanel rootPanel;

    private JTextField txtSearchBar;

    private JButton btnGo;
    private JButton btnViewProfiles;

    private JButton btnCreateProfile;
    private JButton btnUpdateProfile;
    private JButton btnDeleteProfile;
    private JList<String> lstDisplay;
    private JButton btnLogOut;
    private JButton btnMainMenu;
    private JScrollPane scpDisplay;
    private JList<String> lstSearchResults;
    private JPanel pnlListBackground;
    private JLabel lblListInfo;
    private static boolean viewProfiles = true;

    VendorList vendorList = VendorList.getInstance();
    ListModel vendorModel = ListModel.getInstance();  // fix resetting it each time?
    UserDatabase database = UserDatabase.getInstance();

    SearchModel searchModel = new SearchModel();

    MainWindowGUI mainWindowGUI;
    private JLabel lblSearch;

    public VendorUI() {
        mainWindowGUI = MainWindowGUI.getInstance();
        mainWindowGUI.setTitle("Vendor Management");
        addListeners();
        setUpGUI();
    }

    private void setUpGUI() {
        txtSearchBar.requestFocusInWindow();;
        btnGo.setEnabled(false);

       if (database.getCurrentUser().getRole() == EnumUserRoles.PURCHASER) {
           btnViewProfiles.setEnabled(false);
           btnViewProfiles.setVisible(false);
           lstSearchResults.setModel(searchModel.getDisplayListModel());
           lstSearchResults.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);;
           lstDisplay.setVisible(false);
           scpDisplay.setVisible(false);
       } else {
           lstSearchResults.setVisible(false);
           lstDisplay.setModel(vendorModel.getDisplayListModel());
           lstDisplay.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
           lstDisplay.setVisible(!viewProfiles);
           scpDisplay.setVisible(!viewProfiles);
       }
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int idLength = 6;
        int index = -1;
        Object userAction = e.getSource();

        if (userAction == btnGo) {
            if (vendorModel.isEmpty()) {
                displayError("No Vendors to view");
                return;
            }
            int option = confirmSearch();
            String input = txtSearchBar.getText();
            if (option == JOptionPane.CLOSED_OPTION) {
                return;
            } else if (option == JOptionPane.YES_OPTION) {
                if (input.length() <= idLength) {
                    try {
                        int vendorId = Integer.parseInt(input);
                        index = vendorList.getIndex(vendorId);
                    } catch (NumberFormatException ex) {
                        displayError("Not a valid ID.");
                        return;
                    }
                } else {

                }
            } else if (option == JOptionPane.NO_OPTION) {
                index = vendorList.getIndex(input);
            }
            if (index > -1) {
                if (database.getCurrentUser().getRole() == EnumUserRoles.OWNER) {
                    if (viewProfiles) {
                        displayListSettings();
                    }
                    lstDisplay.setSelectedIndex(index);
                } else {
                    searchModel.updateVendor(vendorList.getVendorDetails(index), 0);
                    lstSearchResults.setSelectedIndex(index);
                }
            } else {
                displayError("No Profile Vendor found.");
            }
            txtSearchBar.setText(null);
            btnGo.setEnabled(false);
        } else if (userAction == btnViewProfiles) {
            if (viewProfiles) {
                if (vendorModel.isEmpty()) {
                    displayError("No Vendors to view");
                } else {
                    displayListSettings();
                }
            } else {
                displayListSettings();
            }
        } else if (userAction == btnCreateProfile) {
            mainWindowGUI.setJPanel(new VendorCreation().getPanel());
        } else if (userAction == btnUpdateProfile) {
            if (lstDisplay.isVisible()) {
                index = lstDisplay.getSelectedIndex();
            } else {
                index = lstSearchResults.getSelectedIndex();
            }

            if (index < 0) {
                displayError("Please select a Vendor to update");
            } else {
                mainWindowGUI.setJPanel(new VendorCreation(vendorList.getVendor(index)).getPanel());
            }
        } else if (userAction == btnDeleteProfile) {
            if (lstDisplay.isVisible()) {
                index = lstDisplay.getSelectedIndex();
            } else {
                index = lstSearchResults.getSelectedIndex();
            }
            if (index < 0) {
                displayMessage("Please select a Vendor to delete");
            } else {
                if (deleteWarning() == JOptionPane.YES_OPTION) {
                    if (vendorList.deleteVendor(index)) {
                        /* delete purchase orders here */
                        vendorModel.removeVendor(index);
                        displayMessage("Vendor removed.");
                    } else {
                        displayError("Can only delete when balance = 0.");
                    }
                }
            }
        } else if (userAction == btnLogOut) {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        } else if (userAction == btnMainMenu) {
            mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());
        }
    }

    private void displayListSettings() {
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

    private void displayError(String message) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void displayMessage(String message) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message);
    }

    private int confirmSearch() {
        Object[] options = {"ID", "Full Name"};
        return JOptionPane.showOptionDialog(JOptionPane.getRootFrame(), "Search by:", "Search Options",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

    private int deleteWarning() {
        Object[] options = {"Confirm", "Cancel"};
        return JOptionPane.showOptionDialog(JOptionPane.getRootFrame(),
                "All associated purchase orders will be deleted.", "Warning",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
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