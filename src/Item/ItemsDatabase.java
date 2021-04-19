/**
 * This Class contains implements a list to store, modify, and delete all Item Profiles. it uses a singleton list design pattern
 *
 * @Author Zachary Nicolai
 * @Date 03/15/2021
*/


package src.Item;

import java.util.Vector;

public class ItemsDatabase {
    private static ItemsDatabase firstInstance = null;
    private Vector<Item> itemsList = null;

    private ItemsDatabase(){
        itemsList = new Vector<Item>();
        /*
        Just For testing customer order
         */
        itemsList.add(new Item(1, 1, "mango", 20, "ripe mango", "12/12/2021", 5, "pound", 6));
        itemsList.add(new Item(2, 1, "potato", 12, "sweet potato", "12/12/2020", 3, "pound", 8));
        itemsList.add(new Item(3, 1, "apple", 32, "green apple", "12/12/2020", 5, "pound", 12));
        itemsList.add(new Item(4, 1, "egg", 48, "organic", "12/12/2021", 4, "pound", 17));
        itemsList.add(new Item(5, 1, "milk", 34, "full cream", "12/12/2021", 2, "pound", 9));
        itemsList.add(new Item(6, 1, "rice", 43, "brown", "12/12/2020", 11, "pound", 2));

    }
    public static ItemsDatabase getInstance() {
        if (firstInstance == null) {
            firstInstance = new ItemsDatabase();
        }
        return firstInstance;
    }

    public boolean addItem(Item item){
            itemsList.add(item);
            return true;
    }
    public boolean removeItem(int index){
        itemsList.removeElementAt(index);
        return true;
    }
    public boolean editItem(Item item, int index){
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
        for (Item item : itemsList) {
            items.add(item.toString());
        }
        return items;
    }
    public boolean nameDupe(int id,String name,int vID) {
        Vector<String> items = new Vector<String>();
        for (Item item : itemsList) {
            if(item.getName().equals(name) && item.getVendorId()==vID &&
                    item.getId() != id){
                return true;
            }
        }
        return false;
    }
    public Vector<String> getSearchDetails(String search){
        Vector<String> items = new Vector<String>();
        for (Item item : itemsList) {
            if(String.valueOf(item.getId()).equals(search) || item.getName().equals(search) ||
                    item.getExpirationDate().equals(search)) {
                items.add(item.toString());
            }
        }
        return items;
    }

    public Vector<Item> getItemsForVendor(int vendorId) {
        Vector<Item> items = new Vector<>();
        for (Item item : itemsList) {
            if(vendorId == item.getVendorId()) {
                items.add(item);
            }
        }
        return items;
    }

    public int getId(String s){
        int i=0;
        for (Item item : itemsList) {
            if(s.equals(item.toString())){
                return i;
            }
            i++;
        }
        return -1;
    }
    public Item get(int index) {
        return itemsList.get(index);
    }

    public Item[] getArray() {return itemsList.toArray(new Item[itemsList.size()]);}

    public int size() {return itemsList.size();}
    public boolean isEmpty() {return itemsList.isEmpty();}

}
