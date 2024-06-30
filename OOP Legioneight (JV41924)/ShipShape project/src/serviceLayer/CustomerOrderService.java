package serviceLayer;

import database.database;
import models.CustomerOrder;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CustomerOrderService {

    private Connection connection;

    public CustomerOrderService() {
        this.connection = database.getConnection();
    }

    public List<CustomerOrder> getOrdersForMonth(int month, int year) {

        List<CustomerOrder> orders = new ArrayList<>();

        String sql = "SELECT * FROM customer_orders WHERE MONTH(order_date) = ? AND YEAR(order_date) = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, month);
            statement.setInt(2, year);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CustomerOrder order = new CustomerOrder();
                order.setOrderId(resultSet.getInt("order_id"));
                order.setCustomerName(resultSet.getString("customer_name"));
                order.setOrderDate(resultSet.getDate("order_date"));
                order.setOrderDetails(resultSet.getString("order_details"));
                order.setPrice(resultSet.getDouble("price"));
                order.setCustomerEmail(resultSet.getString("customer_email"));
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public void addCustomerOrder(CustomerOrder order) {
        String sql = "INSERT INTO customer_orders (customer_name, order_date, order_details, price, customer_email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, order.getCustomerName());
            statement.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            statement.setString(3, order.getOrderDetails());
            statement.setDouble(4, order.getPrice());
            statement.setString(5, order.getCustomerEmail());
            statement.executeUpdate();

            // Retrieve the auto-generated order ID
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);
                order.setOrderId(orderId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomerOrder(CustomerOrder order) {
        String sql = "UPDATE customer_orders SET customer_name=?, order_date=?, order_details=?, price=?, customer_email=? WHERE order_id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, order.getCustomerName());
            statement.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            statement.setString(3, order.getOrderDetails());
            statement.setDouble(4, order.getPrice());
            statement.setString(5, order.getCustomerEmail());
            statement.setInt(6, order.getOrderId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeCustomerOrder(int orderId) {
        String sql = "DELETE FROM customer_orders WHERE order_id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CustomerOrder> getAllCustomerOrders() {
        List<CustomerOrder> orders = new ArrayList<>();
        String sql = "SELECT * FROM customer_orders";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CustomerOrder order = new CustomerOrder();
                order.setOrderId(resultSet.getInt("order_id"));
                order.setCustomerName(resultSet.getString("customer_name"));
                order.setOrderDate(resultSet.getDate("order_date"));
                order.setOrderDetails(resultSet.getString("order_details"));
                order.setPrice(resultSet.getDouble("price"));
                order.setCustomerEmail(resultSet.getString("customer_email"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }



}
