package cms;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cms_database";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Thisismysqlpassword1#";
    
     private Connection connection;
     
       public DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Database connected successfully. SUCCESS!!! YAAASSSS");
       
        
       } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }
       
    // Method to close the database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed. GOOD RIDDANCE!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to test the database connection
    public void testConnection() {
        if (connection != null) {
            System.out.println("Database connection test successful.");
        } else {
            System.out.println("Database connection test failed.");
        }
    }
}






