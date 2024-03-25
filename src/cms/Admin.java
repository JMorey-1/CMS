
package cms;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;



public class Admin extends User {

    private DatabaseConnection connection;
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "java";

    // Constructor
    public Admin() {
        super(DEFAULT_USERNAME, DEFAULT_PASSWORD, "admin");
    }
    
    public Admin(String username, String password, String userType, DatabaseConnection connection) {
        super(username, password, userType);
        this.connection = connection;
    }

    // Method to create an office user
    public void createOfficeUser(String username, String password) {
        try {
            String query = "INSERT INTO office_users (office_username, office_password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
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

    // Method to create a lecturer user
   
    
    
    
    public void createLecturerUser(String username, String password) {
        try {
            // Using hardcoded lecturers_id for testing
            int lecturerId = 12; // Replace with the actual lecturers_id
            String query = "UPDATE lecturers SET lecturer_username = ?, lecturer_password = ? WHERE lecturer_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, lecturerId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("New lecturer user created successfully.");
            } else {
                System.out.println("Failed to create new lecturer user.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating lecturer user: " + e.getMessage());
        }
    }



   public void modifyOfficeUsername(String existingUsername, String newUsername) {
    try {
        String query = "UPDATE office_users SET office_username = ? WHERE office_username = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, newUsername);
        statement.setString(2, existingUsername);
        
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Office user's username modified successfully.");
        } else {
            System.out.println("Failed to modify office user's username. User not found.");
        }
    } catch (SQLException e) {
        System.out.println("Error modifying office user's username: " + e.getMessage());
    }
}

   public void modifyOfficePassword(String username, String newPassword) {
    try {
        String query = "UPDATE office_users SET office_password = ? WHERE office_username = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, newPassword);
        statement.setString(2, username);
        
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Office user's password modified successfully.");
        } else {
            System.out.println("Failed to modify office user's password. User not found.");
        }
    } catch (SQLException e) {
        System.out.println("Error modifying office user's password: " + e.getMessage());
    }
}

   public void modifyLecturerUsername(int lecturerId, String newUsername) {
    try {
        String query = "UPDATE lecturers SET lecturer_username = ? WHERE lecturer_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, newUsername);
        statement.setInt(2, lecturerId);
        
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Lecturer's username modified successfully.");
        } else {
            System.out.println("Failed to modify lecturer's username. Lecturer not found.");
        }
    } catch (SQLException e) {
        System.out.println("Error modifying lecturer's username: " + e.getMessage());
    }
}

   public void modifyLecturerPassword(int lecturerId, String newPassword) {
    try {
        String query = "UPDATE lecturers SET lecturer_password = ? WHERE lecturer_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, newPassword);
        statement.setInt(2, lecturerId);
        
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Lecturer's password modified successfully.");
        } else {
            System.out.println("Failed to modify lecturer's password. Lecturer not found.");
        }
    } catch (SQLException e) {
        System.out.println("Error modifying lecturer's password: " + e.getMessage());
    }
}

   public void deleteOfficeUser(String username) {
    try {
        String query = "DELETE FROM office_users WHERE office_username = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Office user deleted successfully.");
        } else {
            System.out.println("Failed to delete office user. User not found.");
        }
    } catch (SQLException e) {
        System.out.println("Error deleting office user: " + e.getMessage());
    }
}

   public void deleteLecturerUser(String username) {
    try {
        String query = "UPDATE lecturers SET lecturer_username = NULL, lecturer_password = NULL WHERE lecturer_username = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Lecturer user deleted successfully.");
        } else {
            System.out.println("Failed to delete lecturer user. User not found.");
        }
    } catch (SQLException e) {
        System.out.println("Error deleting lecturer user: " + e.getMessage());
    }
}

    // Method to change admin's username
   public void changeAdminUsername(String newUsername) {
        setUsername(newUsername);
    }

    // Method to change admin's password
   public void changeAdminPassword(String newPassword) {
        setPassword(newPassword);
    }
}
    
    
    

