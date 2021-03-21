package GUI.ItemManagement;
import GUI.Login.LoginGUI;
import src.Item.ItemsArray;
import GUI.MainWindow.MainWindowGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import src.User.UserDatabase;
import src.User.EnumUserRoles;

public class ItemDisplayGUI {
    private JFrame frame;
    private JButton editButton;
    private JButton leaveButton;
    private JButton deleteButton;
    private JPanel iDisplayPanel;
    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JPanel rootPanel;
    private JLabel contentLabel;
    private JButton logoutButton;
    private int index;

    private MainWindowGUI mainWindowGUI;
    UserDatabase dataBase = UserDatabase.getInstance();

    public ItemDisplayGUI(int i){
        mainWindowGUI = MainWindowGUI.getInstance();

        this.setIndex(i);
        setupGUI();
    }

    private void setupGUI()
    {
        contentLabel.setText(ItemsArray.itemsListToArray(ItemsArray.getItemsList())[index]);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if(dataBase.getCurrentUser().getRole() == EnumUserRoles.OWNER ||dataBase.getCurrentUser().getRole() == EnumUserRoles.PURCHASER) {
                    EditItemsGUI edit = new EditItemsGUI(index);
                    mainWindowGUI.setJPanel(edit.getPanel());
                }
                else{
                    JOptionPane.showMessageDialog(null, "You must be a owner or purchase user");
                }
            }});
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if(dataBase.getCurrentUser().getRole() == EnumUserRoles.OWNER ||dataBase.getCurrentUser().getRole() == EnumUserRoles.PURCHASER) {
                    ItemsArray.remove(index);
                    closeItemEdit();
                }
                else{
                    JOptionPane.showMessageDialog(null, "You must be a owner or purchase user");
                }

            }});
        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeItemEdit();
            }});
        logoutButton.addActionListener(e ->
        {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        });
    }

    public int getIndex(){
        return this.index;
    }
    public void setIndex(int i){
        this.index = i;
    }
    public void closeItemEdit(){
        mainWindowGUI.setJPanel(new ItemsGUI().getPanel());
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
