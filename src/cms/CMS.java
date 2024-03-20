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

/**

 * @author jamie
 */
public class CMS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Initialize database connector
        DatabaseConnection databaseConnection = new DatabaseConnection();
        
        
         // Test database connection
        databaseConnection.testConnection();
        
       // Execute SQL query to retrieve student information
        String query = "SELECT student_id, student_firstname FROM students";
        ResultSet resultSet = databaseConnection.executeQuery(query);

        // Process the result set and print student information
        try {
            System.out.println("Student Information:");
            while (resultSet.next()) {
                int id = resultSet.getInt("student_id");
                String name = resultSet.getString("student_firstname");
                System.out.println("ID: " + id + ", Name: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            databaseConnection.closeConnection();
        }
    }
}
    
    

