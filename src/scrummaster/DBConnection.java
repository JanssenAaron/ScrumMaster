package scrummaster;

import java.sql.*;

public class DBConnection {

    private static final String url = "jdbc:postgresql://localhost:5432/scrum";
    private static final String user = "postgres";
    private static final String password = "Jordago99!";

    public static final Connection CONNECTION = getConnection();

    private static Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Connection successful");
            return con;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
   
}
