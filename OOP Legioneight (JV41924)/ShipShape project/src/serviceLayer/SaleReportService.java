package serviceLayer;

import database.database;
import models.Sale;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleReportService {

    private Connection connection;

    public SaleReportService() {
        this.connection = database.getConnection();
    }

    public List<Sale> getMonthlySalesReport(int month, int year) {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT * FROM sales WHERE MONTH(sale_date) = ? AND YEAR(sale_date) = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, month);
            statement.setInt(2, year);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Sale sale = new Sale();
                sale.setSaleId(resultSet.getInt("sale_id"));
                sale.setItemName(resultSet.getString("item_name"));
                sale.setItemPrice(resultSet.getDouble("item_price"));
                sale.setQuantitySold(resultSet.getInt("quantity_sold"));
                sale.setSaleDate(resultSet.getDate("sale_date"));
                sales.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }

    public double getTotalSalesForMonth(int month, int year) {
        double totalSales = 0;
        String sql = "SELECT SUM(item_price * quantity_sold) AS total_sales FROM sales WHERE MONTH(sale_date) = ? AND YEAR(sale_date) = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, month);
            statement.setInt(2, year);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                totalSales = resultSet.getDouble("total_sales");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalSales;
    }

    public void addSale(Sale sale) {
        String sql = "INSERT INTO sales (item_name, item_price, quantity_sold, sale_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, sale.getItemName());
            statement.setDouble(2, sale.getItemPrice());
            statement.setInt(3, sale.getQuantitySold());
            statement.setDate(4, new java.sql.Date(sale.getSaleDate().getTime()));
            statement.executeUpdate();


            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int saleId = generatedKeys.getInt(1);
                sale.setSaleId(saleId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
