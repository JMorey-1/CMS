
package cms.users;

import cms.DatabaseConnection;
import cms.FileOutput;
import cms.ReportCreator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;




public class Office extends User {
   
    private ReportCreator reportCreator;
    private FileOutput fileOutput;  
    private DatabaseConnection connection;
    private Office office;
    
    public Office(String userName, String password, String userType, ReportCreator reportCreator, DatabaseConnection databaseConnection) {
        // Call the constructor of the superclass (User)
        super(userName, password, userType);
        this.reportCreator = reportCreator;
        this.fileOutput = fileOutput;
        this.connection = databaseConnection;
        
    }
    
    
    // Method to change office user's OWN username
public void changeOfficeUsername(String newUsername, Office office) {
    System.out.println("hello");
    System.out.println(newUsername);
    System.out.println(office.getUsername());
    Connection dbConnection = connection.establishConnection();
    try (PreparedStatement statement = dbConnection.prepareStatement("UPDATE login_details SET username = ? WHERE username = ?")) {
        statement.setString(1, newUsername);
        statement.setString(2, office.getUsername());

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
    
    // Method to change office user's OWN password
// Method to change office user's OWN password
public void changeOfficePassword(String newPassword, Office office) {
   if (office == null) {
        System.out.println("Office object is null. Cannot change password.");
        return;
    }

    System.out.println("hello");
    System.out.println(newPassword);
    System.out.println(office.getUsername());
    Connection dbConnection = connection.establishConnection();
    try (PreparedStatement statement = dbConnection.prepareStatement("UPDATE login_details SET password = ? WHERE username = ?")) {
        statement.setString(1, newPassword);
        statement.setString(2, office.getUsername());

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
    // Method to generate a course report
    public String generateCourseReport() {
       
        if (reportCreator != null) {
            return reportCreator.generateCourseReport();
        } else {
            return "Report creator is not initialized.";
        }
    }
    
    // Method to generate a student report
    public String generateStudentReport(int studentId) {
        
        if (reportCreator != null) {
            return reportCreator.generateStudentReport(studentId);
        } else {
            return "Report creator is not initialized.";
        }
    }
    
    // Method to generate a lecturer report
    public String generateLecturerReport(int lecturerId) {
       
        if (reportCreator != null) {
            return reportCreator.generateLecturerReport(lecturerId);
        } else {
            return "Report creator is not initialized.";
        }
    }
    
}


    
    
    

