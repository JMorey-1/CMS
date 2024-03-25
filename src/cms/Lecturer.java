package cms;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Lecturer extends User {
    private ReportCreator reportCreator;
    private DatabaseConnection connection;
        //attributes specific to lecturer
    
    
    
    // Constructor
      
    public Lecturer(String username, String password, String userType,ReportCreator reportCreator,DatabaseConnection connection) {
        // Call the constructor of the superclass (User) to initialize inherited attributes
        super(username, password, userType);
        this.reportCreator = reportCreator;
        this.connection = connection;
        
       
    }
         // Need to do properly with SQL QUERY
        // Method to change lecturer's own username
           public void changeLecturerUsername(String newUsername) {
           setUsername(newUsername);
          }
    
           
        // Method to change lecturer's own password
        public void changeLecturerPassword(String newPassword) {
        try {
            // Construct the SQL UPDATE statement
            String query = "UPDATE lecturers SET lecturer_password = ? WHERE lecturer_username = ?";
            
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

   
     //NEED A WAY TO GET LECTURER ID
   // Method to generate a lecturer report for the logged-in lecturer
    public String generateLecturerReport() {
    // Get the lecturer ID associated with this object
    int lecturerId = getLecturerId(); 

    // Generate the report for the lecturer using their ID
    return reportCreator.generateLecturerReport(lecturerId);
}
    
    //methods specific to lecturer
    // Can generate a Lecturer Report for themselves
    //Can change their own username and password
}
