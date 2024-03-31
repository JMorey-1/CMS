package cms;



import java.util.Scanner;
import cms.users.User;
import cms.users.Admin;
import cms.users.Office;
import cms.users.Lecturer;


/**
 * The main class for the Course Management System (CMS).
 * Responsible for initializing resources and running the main menu.
 */
public class CMS {
    
    // Initialize database connection, scanner, file output, and report creator
    private static DatabaseConnection databaseConnection = new DatabaseConnection();
    private static Scanner scanner = new Scanner(System.in);
    private static FileOutput fileOutput = new FileOutput();
    private static ReportCreator reportCreator = new ReportCreator(databaseConnection, fileOutput);

   
/**
 * The entry point of the CMS application.
 * Initializes resources and runs the main menu.
 *
 * @param args the command-line arguments
 */   
public static void main(String[] args) {
        try {
            // Create an instance of MenuSystem and pass it's dependencies
            MenuSystem menuSystem = new MenuSystem(databaseConnection, fileOutput, reportCreator);

            // Run the main menu
            menuSystem.runMainMenu();
        } catch (Exception e) {
            // Handle any unexpected exceptions and printing helpful error mesages
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        } finally {
            // Ensure resources are properly closed
            closeResources();
        }
       }

   
 /**
  * Closes resources to prevent resource leaks.
  */
 private static void closeResources() {
        try {
            if (scanner != null) {
                scanner.close(); // Closing my scanner
            }
            if (databaseConnection != null) {
                databaseConnection.closeConnection(); // Closing database connection
            }
        } catch (Exception e) {
            // Log any errors
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}
        
    


    
    

