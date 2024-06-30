package controllers;

import models.Supplier;
import serviceLayer.SupplierService;

import java.util.List;

public class SupplierController {
    private SupplierService service;

    public SupplierController() {
        this.service = new SupplierService();
    }

    public void addSupplier(Supplier supplier) {
        service.addSupplier(supplier);
    }

    public void updateSupplier(Supplier supplier) {
        service.updateSupplier(supplier);
    }

    public void removeSupplier(int supplierId) {
        service.removeSupplier(supplierId);
    }

    public List<Supplier> getAllSuppliers() {
        return service.getAllSuppliers();
    }
}
