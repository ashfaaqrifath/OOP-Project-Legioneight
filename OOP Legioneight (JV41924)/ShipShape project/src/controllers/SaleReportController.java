package controllers;

import models.CustomerOrder;
import serviceLayer.CustomerOrderService;
import java.util.List;

public class SaleReportController {

    private CustomerOrderService orderService;

    public SaleReportController() {
        this.orderService = new CustomerOrderService();
    }

    public List<CustomerOrder> getMonthlySalesReport(int month, int year) {
        return orderService.getOrdersForMonth(month, year);
    }
}
