/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class CMS {


   public static void main(String[] args) {
        // Initialize database connector
        DatabaseConnection databaseConnection = new DatabaseConnection();

        DatabaseConnection dbConnection = new DatabaseConnection();
        Admin admin = new Admin("admin", "java", "admin", dbConnection);
        
        // Test database connection
        databaseConnection.testConnection();

           // Create a DatabaseConnection object
        DatabaseConnection connection = new DatabaseConnection();


        // Call the createOfficeUser method with sample username and password
        admin.createLecturerUser("Ruby", "puppy");

        // Close the database connection
        connection.closeConnection();
    }
        
        // Create instance of ReportCreator
        //ReportCreator reportCreator = new ReportCreator(databaseConnection, null);

        // Generate the report
        //String lecturerReport = reportCreator.generateLecturerReport(1);
        
        //Currently manually passing lecturerid to this method
        //Will be entered by user later
        //Or retrieved form logged in users account
        //reportCreator.generateStudentReport();
         //reportCreator.generateLecturerReport(50);
         
         // Initialize FileOutput object
        //FileOutput fileOutput = new FileOutput();
       // fileOutput.outputReport(lecturerReport, "course_report", "txt");
        
        
       
        
 
        

        // Test writing to a text file
        //System.out.println("Writing report to text file...");
       // fileOutput.writeTextFile(reportContent, "report_text");
        
              // Test writing to a CSV file
       // System.out.println("\nWriting report to CSV file...");
       // fileOutput.writeCSVFile(reportContent, "report_csv");

        // Test outputting report to console
       // System.out.println("\nOutputting report to console...");
        //fileOutput.outputReport(reportContent, "console", null);
    }
        
    

    
    

