package models;

import java.util.Date;

public class CustomerOrder {
    private int orderId;
    private String customerName;
    private Date orderDate;
    private String orderDetails;
    private double price;
    private String customerEmail;

    // Constructors, getters, setters
    public CustomerOrder() {
    }

    public CustomerOrder(int orderId, String customerName, Date orderDate, String orderDetails, Double price, String customerEmail) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.orderDetails = orderDetails;
        this.price = price;
        this.customerEmail = customerEmail;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }

    public String getCustomerEmail(){
        return customerEmail;
    }
    public void setCustomerEmail(String customerEmail){
        this.customerEmail = customerEmail;
    }

    @Override
    public String toString() {
        return "OrderId=" + orderId +
                ", Customer Name='" + customerName + '\'' +
                ", Order Date=" + orderDate +
                ", Order Details='" + orderDetails + '\'' +
                ", Price=$.'"+price +'\'';
    }
}
