package serviceLayer;

import database.database;
import models.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierService {

    private Connection connection;

    public SupplierService() {
        this.connection = database.getConnection();
    }

    public void addSupplier(Supplier supplier) {
        String sql = "INSERT INTO suppliers (supplier_name, contact_person, contact_number, email, country) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, supplier.getSupplierName());
            statement.setString(2, supplier.getContactPerson());
            statement.setString(3, supplier.getContactNumber());
            statement.setString(4, supplier.getSupplierEmail());
            statement.setString(5, supplier.getCountry());
            statement.executeUpdate();


            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int supplierId = generatedKeys.getInt(1);
                supplier.setSupplierId(supplierId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSupplier(Supplier supplier) {
        String sql = "UPDATE suppliers SET supplier_name=?, contact_person=?, contact_number=?, email=?, country=? WHERE supplier_id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, supplier.getSupplierName());
            statement.setString(2, supplier.getContactPerson());
            statement.setString(3, supplier.getContactNumber());
            statement.setString(4, supplier.getSupplierEmail());
            statement.setString(5, supplier.getCountry());
            statement.setInt(6, supplier.getSupplierId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeSupplier(int supplierId) {
        String sql = "DELETE FROM suppliers WHERE supplier_id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, supplierId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM suppliers";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierId(resultSet.getInt("supplier_id"));
                supplier.setSupplierName(resultSet.getString("supplier_name"));
                supplier.setContactPerson(resultSet.getString("contact_person"));
                supplier.setContactNumber(resultSet.getString("contact_number"));
                supplier.setSupplierEmail(resultSet.getString("email"));
                supplier.setCountry(resultSet.getString("country"));
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }
}
