
package cms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.Properties;


public class Admin extends User {

    private DatabaseConnection connection;
    private String adminUsername;
    private String adminPassword;
    private static final String CONFIG_FILE_PATH = "adminconfig.properties";
    private Properties properties;
 
  
    
   public Admin() {
        super(null, null, "admin");
        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(fis);
            fis.close();
            
            // Set adminUsername and adminPassword
            adminUsername = properties.getProperty("default_admin_username", "admin");
            adminPassword = properties.getProperty("default_admin_password", "java");
         
            // Initialize DatabaseConnection
            connection = new DatabaseConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
    
    
 public Admin(String username, String password, String userType, DatabaseConnection databaseConnection) {
        super(username, password, userType);
        this.connection = databaseConnection;
    }

   
// Method to create a new office user
public void createOfficeUser(String username, String password) {
          Connection dbConnection = connection.establishConnection();
          try (PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO login_details (username, password) VALUES (?, ?)")) {
         
            statement.setString(1, username);
            statement.setString(2, password);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("New office user created successfully.");
            } else {
                System.out.println("Failed to create new office user.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating office user: " + e.getMessage());
        }
    }


// Method to create a new lecturer user
public void createLecturerUser(String username, String password, int lecturerId) {
        Connection dbConnection = connection.establishConnection();
        try (PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO login_details (username, password, lecturer_id) VALUES (?, ?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, lecturerId);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("New lecturer user created successfully.");
            } else {
                System.out.println("Failed to create new lecturer user.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating lecturer user: " + e.getMessage());
        }
    }


// Method to change admin's username
public void modifyUsername(String existingUsername, String newUsername) {
          Connection dbConnection = connection.establishConnection();
    try (PreparedStatement statement = dbConnection.prepareStatement("UPDATE login_details SET username = ? WHERE username = ?")) {
        statement.setString(1, newUsername);
        statement.setString(2, existingUsername);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("User's username modified successfully.");
        } else {
            System.out.println("Failed to modify user's username. User not found.");
        }
    } catch (SQLException e) {
        System.out.println("Error modifying user's username: " + e.getMessage());
    }
}


// Method to change a users password
public void modifyPassword(String username, String newPassword) {
    Connection dbConnection = connection.establishConnection();
    try (PreparedStatement statement = dbConnection.prepareStatement("UPDATE login_details SET password = ? WHERE username = ?")) {
        statement.setString(1, newPassword);
        statement.setString(2, username);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("User's password modified successfully.");
        } else {
            System.out.println("Failed to modify user's password. User not found.");
        }
    } catch (SQLException e) {
        System.out.println("Error modifying user's password: " + e.getMessage());
    }
}


// Method to delete a user from the database, use carefully!
public void deleteUser(String username) {
    Connection dbConnection = connection.establishConnection();
    
        try( PreparedStatement statement = dbConnection.prepareStatement("DELETE FROM login_details WHERE username = ?")) {
        statement.setString(1, username);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("Failed to delete user. User not found.");
        }
    } catch (SQLException e) {
        System.out.println("Error deleting user: " + e.getMessage());
    }
}


public String getAdminUsername() {
        return properties.getProperty("default_admin_username", "admin");
    }

 public String getAdminPassword() {
        return properties.getProperty("default_admin_password", "java");
    }

  public void setAdminUsername(String newAdminUsername) {
        properties.setProperty("default_admin_username", newAdminUsername);
        saveProperties();
    }
  
  
  
    public void setAdminPassword(String newAdminPassword) {
        properties.setProperty("default_admin_password", newAdminPassword);
        saveProperties();
    }

     private void saveProperties() {
        try {
            FileOutputStream fos = new FileOutputStream(CONFIG_FILE_PATH);
            properties.store(fos, null);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    
    
    

}