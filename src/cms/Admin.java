
package cms;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;



public class Admin extends User {

    private DatabaseConnection connection;

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
        Lecturer lecturer = new Lecturer(username, password, "lecturer");
        // Logic to add the lecturer user to the system
    }

    











// Method to modify an existing user's username and password
    public void modifyUser(String existingUsername, String newUsername, String newPassword) {
        // Logic to find and modify the user's details
    }

    // Method to delete an existing user
    public void deleteUser(String username) {
        // Logic to find and delete the user
    }

    // Method to change admin's own username
    public void changeAdminUsername(String newUsername) {
        setUsername(newUsername);
    }

    // Method to change admin's own password
    public void changeAdminPassword(String newPassword) {
        setPassword(newPassword);
    }
}
    
    
    
    
    
    //methods specific to Admin
      //The only user available at the start
      //Username: admin■ Password: java
      //Can add, modify and delete other users (including username, password and role)
      // Can change their own username and password
       //Cannot generate reports
    
    

