
package cms.users;

import cms.DatabaseConnection;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Properties;

/**
 * Represents an admin user in the Course Management System (CMS).
 * Extends the User class.
 * This class provides functionality for managing admin-related tasks.
 * For security purposes I decided to use a config file to store the admin
 * password and username, separate to the database, the original plan was to have this encrypted but I 
 * unfortunately ran out of time
 */
public class Admin extends User {

    private DatabaseConnection connection;
    private String adminUsername;
    private String adminPassword;
    //The path to the configuration file for admin properties.
    private static final String CONFIG_FILE_PATH = "adminconfig.properties";
    /** The properties object to store admin configuration settings. */
    private Properties properties;
 
  
/**
 * Constructs a new Admin object with default properties.
 */    
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
  
    
 /**
  * Constructs a new Admin object with the specified username, password, user type, and database connection.
  *
  * @param username the username of the admin
  * @param password the password of the admin
  * @param userType the user type of the admin
  * @param databaseConnection the database connection instance
  */   
 public Admin(String username, String password, String userType, DatabaseConnection databaseConnection) {
        super(username, password, userType);
        this.connection = databaseConnection;
    }

   
 /**
  * Creates a new office user with the specified username and password.
  *
  * @param username the username of the new office user
  * @param password the password of the new office user
  */
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


 /**
  * Creates a new lecturer user with the specified username, password, and lecturer ID.
  *
  * @param username the username of the new lecturer user
  * @param password the password of the new lecturer user
  * @param lecturerId the ID of the lecturer
  * Due to my current database setup this ID should be an int between 1 and 100.
  */
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


 /**
  * Modifies the username of a user from the existing username to the new username.
  *
  * @param existingUsername the existing username to be modified
  * @param newUsername the new username to replace the existing username
  */
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

/**
 * Changes the role of a lecturer user identified by the username.
 *
 * @param username the username of the lecturer whose role is to be changed
 * @param newRole the new role to be assigned to the lecturer
 */
public void changeLecturerRole(String username, String newRole) {
    Connection dbConnection = connection.establishConnection();
    try (PreparedStatement statement = dbConnection.prepareStatement("UPDATE lecturers SET role = ? WHERE lecturer_id = (SELECT lecturer_id FROM login_details WHERE username = ?)")) {
        statement.setString(1, newRole);
        statement.setString(2, username);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Lecturer's role modified successfully.");
        } else {
            System.out.println("Failed to modify lecturer's role. Lecturer not found.");
        }
    } catch (SQLException e) {
        System.out.println("Error modifying lecturer's role: " + e.getMessage());
    }
}

/**
 * Modifies the password of a user identified by the username.
 *
 * @param username the username of the user whose password is to be modified
 * @param newPassword the new password to replace the existing password
 */
public void modifyUserPassword(String username, String newPassword) {
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


/**
 * Deletes a user from the database using the specified username.
 * Use this method with caution.
 *
 * @param username the username of the user to be deleted
 */
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

 /**
  * Retrieves the default admin username.
  *
  * @return the default admin username
  */
public String getAdminUsername() {
        return properties.getProperty("default_admin_username", "admin");
    }
/**
 * Retrieves the default admin password.
 *
 * @return the default admin password
 */
public String getAdminPassword() {
        return properties.getProperty("default_admin_password", "java");
    }
 /**
  * Sets the default admin username.
  *
  * @param newAdminUsername the new admin username
  */
public void setAdminUsername(String newAdminUsername) {
        properties.setProperty("default_admin_username", newAdminUsername);
        saveProperties();
    }
  
  
/**
 * Sets the default admin password.
 *
 * @param newAdminPassword the new admin password
 */  
public void setAdminPassword(String newAdminPassword) {
        properties.setProperty("default_admin_password", newAdminPassword);
        saveProperties();
    }
/**
 * Saves the admin properties to the configuration file.
 */
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