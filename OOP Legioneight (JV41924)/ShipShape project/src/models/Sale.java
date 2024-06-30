package models;

import java.util.Date;

public class Sale {

    private int saleId;
    private String itemName;
    private double itemPrice;
    private int quantitySold;
    private Date saleDate;

    // Constructors, getters, setters
    public Sale() {
    }

    public Sale(int saleId, String itemName, double itemPrice, int quantitySold, Date saleDate) {
        this.saleId = saleId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantitySold = quantitySold;
        this.saleDate = saleDate;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "saleId=" + saleId +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", quantitySold=" + quantitySold +
                ", saleDate=" + saleDate +
                '}';
    }
}
