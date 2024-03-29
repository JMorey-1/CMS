
package cms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


public class CMS {
   private FileOutput fileOutput;
    private Scanner scanner;
    private DatabaseConnection connection;
    private Admin admin;
    private Office office;
    private ReportCreator reportCreator;


   public static void main(String[] args) {
        // Initialize database connector
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Scanner scanner = new Scanner(System.in);
        FileOutput fileOutput = new FileOutput();
       
                 
        ReportCreator reportCreator = new ReportCreator(databaseConnection, fileOutput);
       // Define the database connection details
       
      // Create an instance of MenuSystem
        MenuSystem menuSystem = new MenuSystem(databaseConnection,fileOutput,reportCreator
        );

        // Run the main menu
        menuSystem.runMainMenu();

   }
}
      
        
    


    
    

