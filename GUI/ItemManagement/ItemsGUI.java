/*
 * Name:Zachary Nicolai
 * Class Name: ItemsGUI
 * Class Description: This class controls the ItemsGUI. it gives instructions for all of the buttons and text fields. It
 * allows users to double click an item to access ItemDisplayGUI, access addItemsGUI if the add button is pressed, or search
 * for an Item. it ensures that only the correct types of users can do specific tasks.
 * */


package GUI.ItemManagement;

import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import src.Item.Items;
import src.Item.ItemsArray;
import src.User.UserDatabase;
import src.User.EnumUserRoles;
import GUI.MainWindow.MainWindowGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Vector;


public class ItemsGUI implements FocusListener{

    private JPanel rootPanel;

    private JTextField searchField;
    private JButton searchButton;
    private JButton addButton;
    private JButton displayButton;
    private JButton leaveButton;
    private JButton logoutButton;
    private JList iList;
    private JLabel guiLabel;
    private JTextField focused = searchField;
    private ArrayList<Items> itemsListCopy;
    private MainWindowGUI mainWindowGUI;
    ItemsArray itemsList = ItemsArray.getInstance();


    UserDatabase dataBase = UserDatabase.getInstance();
    NumberFormat nf = new DecimalFormat();

    public ItemsGUI(){
        mainWindowGUI = MainWindowGUI.getInstance();
        setupGUI();
    }

    public void setupGUI()
    {
        //button actions
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if(dataBase.getCurrentUser().getRole() == EnumUserRoles.OWNER ||dataBase.getCurrentUser().getRole() == EnumUserRoles.PURCHASER) {
                    String searchedItem = searchField.getText();
                    setCatalog(itemsList.getSearchDetails(searchedItem));
                    if(iList.getModel().getSize() == 0 ){
                        JOptionPane.showMessageDialog(null, searchedItem + " not found");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,  "Must be Owner user or Purchase user");
                }
            }});
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (dataBase.getCurrentUser().getRole() == EnumUserRoles.OWNER || dataBase.getCurrentUser().getRole() ==
                        EnumUserRoles.PURCHASER ||dataBase.getCurrentUser().getRole() == EnumUserRoles.INVENTORY_MANAGER) {
                    mainWindowGUI.setJPanel(new AddItemsGUI().getPanel());
                }
                else{
                    JOptionPane.showMessageDialog(null, "You must be a Owner, Purchaser, or Inventory Manager");
                }
            }
            });
        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeCatalog();
            }});
        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (dataBase.getCurrentUser().getRole() == EnumUserRoles.OWNER) {
                    setCatalog(itemsList.getAllItemDetails());
                }
                else{
                    JOptionPane.showMessageDialog(null, "Only Owners can view a list of all items in the system");
                }
            }});

        iList.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
                try {
                    JList jList = (JList) evt.getSource();
                    if (evt.getClickCount() == 2) {
                        //index of selected jList is stored and then ItemsGUI displays selectedIndex
                        int index = searchIndex(iList.locationToIndex(evt.getPoint()));
                        ItemDisplayGUI dis = new ItemDisplayGUI(index);
                        mainWindowGUI.setJPanel(dis.getPanel());
                    }
                }
                catch(ArrayIndexOutOfBoundsException a){
                    JOptionPane.showMessageDialog(null, "Please make sure you have selected an item.");
                }
            }
        });
        logoutButton.addActionListener(e ->
        {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        });
    }
    public void setCatalog(Vector v){
        iList.setListData(v);
        iList.setFont(new Font("Arial",Font.BOLD,12));
    }
    public int searchIndex(int index){
        String itemsString;
        System.out.println(iList.getModel().getElementAt(index));
        itemsString = (String) iList.getModel().getElementAt(index);
        return itemsList.getId(itemsString);
    }
    public void closeCatalog(){
        mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());
    }

    //methods required for implementing focusListener
    public void focusGained(FocusEvent e) {
        if (e.getSource() instanceof JTextField) {
            focused = (JTextField) e.getSource();
        }
    }
    public void focusLost(FocusEvent e) {
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
