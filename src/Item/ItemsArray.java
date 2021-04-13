/*
 * Author: Zachary Nicolai
 * Class Name: ItemsArray
 * Class Description: This Class contains the ItemsArray ItemsList which stores all of the item profiles
 * as an Item Object. the constructor adds a new Items object to the Array list. It contains methods to set
 * a variable of Items object in the ArrayList as well as methods that return the itemsList as a String array or as an
 * arrayList.
*/


package src.Item;

import java.util.Vector;

public class ItemsArray {
    private static ItemsArray firstInstance = null;
    private Vector<Items> itemsList = null;

    private ItemsArray(){
        itemsList = new Vector<Items>();
    }
    public static ItemsArray getInstance() {
        if (firstInstance == null) {
            firstInstance = new ItemsArray();
        }
        return firstInstance;
    }

    public boolean addItem(Items item){
            itemsList.add(item);
            return true;
    }
    public boolean removeItem(int index){
        itemsList.removeElementAt(index);
        return true;
    }
    public boolean editItem(Items item,int index){
        itemsList.get(index).setId(item.getId());
        itemsList.get(index).setName(item.getName());
        itemsList.get(index).setVendorId(item.getVendorId());
        itemsList.get(index).setExpirationDate(item.getExpirationDate());
        itemsList.get(index).setPurchasePrice(item.getPurchasePrice());
        itemsList.get(index).setQuantity(item.getQuantity());
        itemsList.get(index).setSellingPrice(item.getSellingPrice());
        itemsList.get(index).setUnit(item.getUnit());
        itemsList.get(index).setCategory(item.getCategory());
        return true;
    }

    public Vector<String> getAllItemDetails() {
        Vector<String> items = new Vector<String>();
        for (Items item : itemsList) {
            items.add(item.toString());
        }
        return items;
    }
    public boolean nameDupe(int id,String name,int vID) {
        Vector<String> items = new Vector<String>();
        for (Items item : itemsList) {
            if(item.getName().equals(name) && item.getVendorId()==vID &&
                    item.getId() != id){
                return true;
            }
        }
        return false;
    }
    public Vector<String> getSearchDetails(String search){
        Vector<String> items = new Vector<String>();
        for (Items item : itemsList) {
            if(String.valueOf(item.getId()).equals(search) || item.getName().equals(search) ||
                    item.getExpirationDate().equals(search)) {
                items.add(item.toString());
            }
        }
        return items;
    }
    public Items get(int index) {
        return itemsList.get(index);
    }


}
