package cms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MenuSystem {
    private Scanner scanner;
    private DatabaseConnection connection;
    
    public MenuSystem(DatabaseConnection connection) {
        this.connection = connection;
        this.scanner = new Scanner(System.in);
    }
    
    
     public boolean authenticate(String username, String password) {
        // Check if admin
        if (username.equals("admin") && password.equals("java")) {
            return true;
        } else {
            // Check if office user or lecturer
            try {
                // Try to authenticate against the office_users table
                if (authenticateAgainstDBTables(username, password)) {
                    return true;
                }
                
                // Try to authenticate against the lecturers table
                if (authenticateAgainstDBTables(username, password)) {
                    return true;
                }
            } catch (SQLException e) {
                System.out.println("Error authenticating user: " + e.getMessage());
                return false; // Return false in case of SQL exception
            }
            
            // Access denied
            return false;
        }
    }
    
    
     private boolean authenticateAgainstDBTables(String username, String password) throws SQLException {
    String officeUsersQuery = "SELECT * FROM office_users WHERE office_username = ? AND office_password = ?";
    String lecturersQuery = "SELECT * FROM lecturers WHERE lecturer_username = ? AND lecturer_password = ?";
    
    try (Connection dbConnection = connection.establishConnection();
         PreparedStatement officeUsersStatement = dbConnection.prepareStatement(officeUsersQuery);
         PreparedStatement lecturersStatement = dbConnection.prepareStatement(lecturersQuery)) {
        
        // Set parameters for office_users query
        officeUsersStatement.setString(1, username);
        officeUsersStatement.setString(2, password);
        
        // Execute query for office_users
        try (ResultSet officeUsersResultSet = officeUsersStatement.executeQuery()) {
            // Check if any rows are returned
            if (officeUsersResultSet.next()) {
                return true; // Authentication successful
            }
        }
        
        // Set parameters for lecturers query
        lecturersStatement.setString(1, username);
        lecturersStatement.setString(2, password);
        
        // Execute query for lecturers
        try (ResultSet lecturersResultSet = lecturersStatement.executeQuery()) {
            // Check if any rows are returned
            if (lecturersResultSet.next()) {
                return true; // Authentication successful
            }
        }
        
        // Authentication failed for both tables
        return false;
    } catch (SQLException e) {
        throw new SQLException("Error authenticating user: " + e.getMessage());
    }
}
    



    public void displayMainMenu() {
        System.out.println("Main Menu:");
        System.out.println("1. Login");
        System.out.println("2. Exit");
    }

    public int getMenuChoice() {
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }

    public void closeScanner() {
        scanner.close();
    }

    public void runMainMenu() {
        int choice;
        do {
            displayMainMenu();
            choice = getMenuChoice();
            switch (choice) {
                case 1:
                    processLogin();
                    break;
                case 2:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 2);
    }

    public void run() {
        runMainMenu();
        // Other menu logic goes here
       }

    public void processLogin() {
    scanner = new Scanner(System.in);
    System.out.println("Login:");
    System.out.print("Enter username: ");
    String username = scanner.next();
    System.out.print("Enter password: ");
    String password = scanner.next();

    // Call the authentication method
    if (authenticate(username, password)) {
        System.out.println("Login successful!");
        // Proceed to the appropriate submenu
        // For now, let's print a message indicating which user type logged in
    } else {
        System.out.println("Invalid username or password. Please try again.");
    }
}



}

