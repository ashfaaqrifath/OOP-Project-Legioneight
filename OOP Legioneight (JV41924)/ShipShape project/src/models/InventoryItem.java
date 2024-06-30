package models;

public class InventoryItem {

    private int itemId;
    private String itemName;
    private int quantity;
    private double price;

    // Constructors, getters, setters
    public InventoryItem() {
    }

    public InventoryItem(int itemId, String itemName, int quantity, double price) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }

    @Override
    public String toString() {
        return "ItemId=" + itemId +
                ", ItemName='" + itemName + '\'' +
                ", Quantity='" + quantity + '\'' +
                ",  Price='$." + price + '\'';
    }
}
