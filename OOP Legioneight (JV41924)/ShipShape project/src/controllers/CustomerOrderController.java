package controllers;

import models.CustomerOrder;
import serviceLayer.CustomerOrderService;

import java.util.List;

public class CustomerOrderController {
    private CustomerOrderService service;

    public CustomerOrderController() {
        this.service = new CustomerOrderService();
    }

    public void addCustomerOrder(CustomerOrder order) {
        service.addCustomerOrder(order);
    }

    public void updateCustomerOrder(CustomerOrder order) {
        service.updateCustomerOrder(order);
    }

    public void removeCustomerOrder(int orderId) {
        service.removeCustomerOrder(orderId);
    }

    public List<CustomerOrder> getAllCustomerOrders() {
        return service.getAllCustomerOrders();
    }
}
