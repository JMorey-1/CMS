
package cms.users;

import cms.DatabaseConnection;
import cms.FileOutput;
import cms.ReportCreator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



/**
 * Represents an office user in the CMS system.
 * This class provides functionality for managing office-related tasks.
 */
public class Office extends User {
   
    private ReportCreator reportCreator; 
    private DatabaseConnection connection;
  
    
    
/**
 * Constructs a new Office object with the specified attributes.
 *
 * @param userName         Username of the office user.
 * @param password         The password of the office user.
 * @param userType         The type of user(Office, Admin, Lecturer).
 * @param reportCreator    The report creator utility used for generating reports.
 * @param databaseConnection The database connection used for database operations.
 */  
 public Office(String userName, String password, String userType, ReportCreator reportCreator, DatabaseConnection databaseConnection) {
        // Call the constructor of the superclass (User)
        super(userName, password, userType);
        this.reportCreator = reportCreator;
        this.connection = databaseConnection;
        
    }
    
  /**
     * Changes the office user's own username in the database table login_details.
     *
     * @param newUsername The new username to set for the office user.
     * @param office      The Office object representing the user.
     */
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
    
 


/**
 * Changes the office user's own password in the database table login_details.
 *
 * @param newPassword The new password to set for the office user.
 * @param office      The Office object representing the user.
 */
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

    


  /**
     * Generates a course report.
     * 
     * This method calls the {@link ReportCreator#generateCourseReport()} method to generate the report content.
     *
     * @return A string containing the course report.
     */
public String generateCourseReport() {
       
        if (reportCreator != null) {
            return reportCreator.generateCourseReport();
        } else {
            return "Report creator is not initialized.";
        }
    }
    
/**
 * Generates a student report.
 * This method calls the {@link  ReportCreator#generateStudentReport(int)} method to generate the report content.
 *
 * @param studentId The ID of the student for whom the report is generated.
 * Due to my current database setup this ID should be an int between 1 and 300.
 * @return A string containing the student report.
 */  
public String generateStudentReport(int studentId) {
        
        if (reportCreator != null) {
            return reportCreator.generateStudentReport(studentId);
        } else {
            return "Report creator is not initialized.";
        }
    }
   


/**
 * Generates a lecturer report.
 * This method calls the {@link ReportCreator#generateLecturerReport(int)} method to generate the report content.
 
 * @param lecturerId The ID of the lecturer for whom the report is generated.
 * Due to my current database setup this ID should be an int between 1 and 100.
 * @return A string containing the lecturer report.
 */
public String generateLecturerReport(int lecturerId) {
       
        if (reportCreator != null) {
            return reportCreator.generateLecturerReport(lecturerId);
        } else {
            return "Report creator is not initialized.";
        }
    }
    
}


    
    
    

