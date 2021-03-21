/*
 * Author: Zachary Nicolai
 * Class Name: ItemsArray
 * Class Description: This Class contains the ItemsArray ItemsList which stores all of the item profiles
 * as an Item Object. the constructor adds a new Items object to the Array list. It contains methods to set
 * a variable of Items object in the ArrayList as well as methods that return the itemsList as a String array or as an
 * arrayList.
*/


package src.Item;

import java.util.ArrayList;

public class ItemsArray {
    //itemsList that stores all item profiles
    public static ArrayList<Items> itemsList = new ArrayList<Items>();

    //constructor that adds a new items object to the ArrayList
    public ItemsArray(int i,int vi,String n,double sp,String c,String ed,double pp,String u,int q){
        itemsList.add(new Items(i,vi,n,sp,c,ed,pp,u,q));
    }

    //set methods
    public static void setIndexID(int i,int s){
        itemsList.get(i).setId(s);
    }
    public static void setIndexName(int i,String s){
        itemsList.get(i).setName(s);
    }
    public static void setIndexVId(int i,int s){
        itemsList.get(i).setVendorId(s);
    }
    public static void setIndexSPrice(int i,double s){
        itemsList.get(i).setSellingPrice(s);
    }
    public static void setIndexCategory(int i,String s){
        itemsList.get(i).setCategory(s);
    }
    public static void setIndexExpiration(int i,String s){
        itemsList.get(i).setExpirationDate(s);
    }
    public static void setIndexPPrice(int i,double s){
        itemsList.get(i).setPurchasePrice(s);
    }
    public static void setIndexUnit(int i,String s){
        itemsList.get(i).setUnit(s);
    }
    public static void setIndexQuantity(int i,int s){
        itemsList.get(i).setQuantity(s);
    }

    //removes a Item object from the itemsList
    public static void remove(int index){
        itemsList.remove(index);
    }

    //returns itemsList
    public static ArrayList<Items> getItemsList() {
        return itemsList;
    }

    //returns itemsList as a StringArray
    public static String[] itemsListToArray(ArrayList<Items> tempList) {
        String[] tempListArray = new String[tempList.size()];
        for (int i = 0; i < tempList.size(); i++) {
            tempListArray[i] = tempList.get(i).toString();
        }
        return tempListArray;
    }


}
