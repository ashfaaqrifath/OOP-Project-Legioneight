package serviceLayer;

import database.database;
import models.InventoryItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class InventoryService {

    private Connection connection;

    public InventoryService() {
        this.connection = database.getConnection();
    }

    public void addInventoryItem(InventoryItem item) {
        String sql = "INSERT INTO inventory_items (item_name, quantity, price) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getItemName());
            statement.setInt(2, item.getQuantity());
            statement.setDouble(3, item.getPrice());
            statement.executeUpdate();


            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int itemId = generatedKeys.getInt(1);
                item.setItemId(itemId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateInventoryItem(InventoryItem item) {
        String sql = "UPDATE inventory_items SET item_name=?, quantity=?, price=? WHERE item_id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getItemName());
            statement.setInt(2, item.getQuantity());
            statement.setDouble(3, item.getPrice());
            statement.setInt(4, item.getItemId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeInventoryItem(int itemId) {
        String sql = "DELETE FROM inventory_items WHERE item_id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, itemId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<InventoryItem> getAllInventoryItems() {
        List<InventoryItem> items = new ArrayList<>();
        String sql = "SELECT * FROM inventory_items";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                InventoryItem item = new InventoryItem();
                item.setItemId(resultSet.getInt("item_id"));
                item.setItemName(resultSet.getString("item_name"));
                item.setQuantity(resultSet.getInt("quantity"));
                item.setPrice(resultSet.getDouble("price"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}
