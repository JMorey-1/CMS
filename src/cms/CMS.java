/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cms;

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
    }
    
}
