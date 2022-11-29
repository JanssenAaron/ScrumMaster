package scrummaster;

import java.sql.*;

public class DBConnection {

    private static final String url = "";
    private static final String user = "";
    private static final String password = "";

    public static final Connection CONNECTION = getConnection();

    private static Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Connection successful");
            return con;
        } catch (SQLException e) {
            System.out.println("Connection failed");
            return null;
        }
    }
   
}
