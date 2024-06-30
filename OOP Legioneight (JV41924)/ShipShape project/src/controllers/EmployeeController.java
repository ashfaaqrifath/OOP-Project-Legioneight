package controllers;

import models.Employee;
import serviceLayer.EmployeeService;

import java.util.List;

public class EmployeeController {
    private EmployeeService service;

    public EmployeeController() {
        this.service = new EmployeeService();
    }

    public void addEmployee(Employee employee) {
        service.addEmployee(employee);
    }

    public void updateEmployee(Employee employee) {
        service.updateEmployee(employee);
    }

    public void removeEmployee(int employeeId) {
        service.removeEmployee(employeeId);
    }

    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }
}
