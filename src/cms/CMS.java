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

        // Test database connection
        databaseConnection.testConnection();

        // Create instance of ReportCreator
        ReportCreator reportCreator = new ReportCreator(databaseConnection, null);

        // Generate the course report
        //reportCreator.generateCourseReport();
        
        
        reportCreator.generateStudentReport();
    }
}
    
    

