package Item;

import java.util.ArrayList;

public class ItemsArray {
    public static ArrayList<Items> itemsList = new ArrayList<Items>();

    public ItemsArray(int i,int vi,String n,double sp,String c,String ed,double pp,String u,int q){
        itemsList.add(new Items(i,vi,n,sp,c,ed,pp,u,q));
    }

    public static String[] itemsListToArray(ArrayList<Items> tempList) {
        String[] tempListArray = new String[tempList.size()];
        for(int i=0;i<tempList.size();i++){
            tempListArray[i] = tempList.get(i).toString();
        }
        return tempListArray;
    }
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


    public static void remove(int index){
        itemsList.remove(index);
    }

    public static ArrayList<Items> getItemsList() {
        return itemsList;
    }


}
