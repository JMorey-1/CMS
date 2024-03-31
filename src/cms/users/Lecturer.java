package cms.users;
import cms.DatabaseConnection;
import cms.ReportCreator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Lecturer extends User {
    
    
    private ReportCreator reportCreator;
    private DatabaseConnection connection;
    private int lecturerId;
    
  
      
        
    
 public Lecturer(String username, String password, String userType, int LecturerId, ReportCreator reportCreator) {
        
        // Call the constructor of the superclass USER
        super(username, password, userType);
        this.reportCreator = reportCreator;
        
       
    }
         
 // Method to change lecturer's own username
 public void changecurrentLecturerUsername(int lecturerId, String newUsername) {
            try {
            // Construct the SQL UPDATE statement
            String query = "UPDATE login_details SET username = ? WHERE lecturer_username = ?";
            
            // Get a connection to the database
            Connection conn = connection.establishConnection();
            
            // Create a PreparedStatement to execute the SQL query
            PreparedStatement statement = conn.prepareStatement(query);
            
            // Set the parameters in the prepared statement
            
            statement.setString(1, getUsername()); // Assuming getUsername() retrieves the lecturer's username
            
            // Execute the update query
            int rowsAffected = statement.executeUpdate();
            
            // Check if the update was successful
            if (rowsAffected > 0) {
                System.out.println("Password updated successfully.");
            } else {
                System.out.println("Failed to update password.");
            }
            
            // Closing the statement and connection
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error updating password: " + e.getMessage());
        }
    }
          
    
           
 // Method to change lecturer's own password
  public void changecurrentLecturerPassword(int lecturerId, String newPassword) {
        try {
            // Construct the SQL UPDATE statement
            String query = "UPDATE login_details SET password = ? WHERE lecturer_username = ?";
            
            // Get a connection to the database
            Connection conn = connection.establishConnection();
            
            // Create a PreparedStatement to execute the SQL query
            PreparedStatement statement = conn.prepareStatement(query);
            
            // Set the parameters in the prepared statement
            statement.setString(1, newPassword);
            statement.setString(2, getUsername()); // Assuming getUsername() retrieves the lecturer's username
            
            // Execute the update query
            int rowsAffected = statement.executeUpdate();
            
            // Check if the update was successful
            if (rowsAffected > 0) {
                System.out.println("Password updated successfully.");
            } else {
                System.out.println("Failed to update password.");
            }
            
            // Closing the statement and connection
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error updating password: " + e.getMessage());
        }
        
        }
        
        
 // Method to generate a lecturer report
 public String generatecurrentLecturerReport(int lecturerId) {
        System.out.println("test test test");
        if (reportCreator != null) {
            return reportCreator.generateLecturerReport(lecturerId);
        } else {
            return "Report creator is not initialized.";
        }
    }
}