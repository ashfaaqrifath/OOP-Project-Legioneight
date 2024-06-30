package serviceLayer;

import database.database;
import models.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private Connection connection;

    public EmployeeService() {
        this.connection = database.getConnection();
    }

    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO employees (full_name, job_role, contact_number, email_address) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, employee.getFullName());
            statement.setString(2, employee.getJobRole());
            statement.setString(3, employee.getContactNumber());
            statement.setString(4, employee.getEmailAddress());
            statement.executeUpdate();


            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int employeeId = generatedKeys.getInt(1);
                employee.setEmployeeId(employeeId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(Employee employee) {
        String sql = "UPDATE employees SET full_name=?, job_role=?, contact_number=?, email_address=? WHERE employee_id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employee.getFullName());
            statement.setString(2, employee.getJobRole());
            statement.setString(3, employee.getContactNumber());
            statement.setString(4, employee.getEmailAddress());
            statement.setInt(5, employee.getEmployeeId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeEmployee(int employeeId) {
        String sql = "DELETE FROM employees WHERE employee_id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(resultSet.getInt("employee_id"));
                employee.setFullName(resultSet.getString("full_name"));
                employee.setJobRole(resultSet.getString("job_role"));
                employee.setContactNumber(resultSet.getString("contact_number"));
                employee.setEmailAddress(resultSet.getString("email_address"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
