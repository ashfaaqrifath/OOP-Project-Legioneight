package controllers;

import models.InventoryItem;
import serviceLayer.InventoryService;

import java.util.List;

public class InventoryController {
    private InventoryService service;

    public InventoryController() {
        this.service = new InventoryService();
    }

    public void addInventoryItem(InventoryItem item) {
        service.addInventoryItem(item);
    }

    public void updateInventoryItem(InventoryItem item) {
        service.updateInventoryItem(item);
    }

    public void removeInventoryItem(int itemId) {
        service.removeInventoryItem(itemId);
    }

    public List<InventoryItem> getAllInventoryItems() {
        return service.getAllInventoryItems();
    }
}
