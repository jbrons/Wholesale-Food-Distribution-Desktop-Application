package GUI;

import Item.ItemsArray;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
    private int index;

    private void createUIComponents() {

    }
    public ItemDisplayGUI(int i){
        frame = new JFrame("Item Display");
        frame.setTitle("Item Display");
        this.setIndex(i);
       contentLabel.setText(ItemsArray.itemsListToArray(ItemsArray.getItemsList())[index]);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                EditItemsGUI edit = new EditItemsGUI(index);
                edit.displayItemEdit();
            }});
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ItemsArray.remove(index);
                closeItemEdit();
            }});
        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeItemEdit();
            }});
    }
    public void window() {
        frame.setContentPane(new ItemDisplayGUI(index).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();
    }
    public int getIndex(){
        return this.index;
    }
    public void setIndex(int i){
        this.index = i;
    }
    public void closeItemEdit(){
        SwingUtilities.getWindowAncestor(rootPanel).setVisible(false);
    }
}
