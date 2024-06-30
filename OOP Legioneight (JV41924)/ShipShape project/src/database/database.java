package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {

                String url = "jdbc:mysql://localhost:3306/shipshape?useSSL=false";
                String username = "root";
                String password = "";
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
