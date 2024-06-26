package cms;

import cms.users.Office;
import cms.users.Lecturer;
import cms.users.Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * The menu system of the Course Management System (CMS).
 * Responsible for displaying menus, processing user input, and navigating between different user interfaces.
 */
public class MenuSystem {
    private Scanner scanner;
    private DatabaseConnection connection;
    private Admin admin;
    private Office office;
    private FileOutput fileOutput;
    private ReportCreator reportCreator;
    private Lecturer lecturer;
    private int lecturerId;
            

    
    
/**
 * Constructs a new MenuSystem object.
 * Initializes the scanner, database connection, admin instance, file output, and report creator.
 * 
 * @param connection the database connection instance
 * @param fileOutput the file output instance
 * @param reportCreator the report creator instance
 */   
public MenuSystem(DatabaseConnection connection, FileOutput fileOutput, ReportCreator reportCreator) {
    try {
        this.connection = connection; 
        this.admin = new Admin();
        this.office = null;
        this.lecturer = null;
        this.scanner = new Scanner(System.in);
        this.fileOutput = fileOutput; // Assigned fileOutput passed to constructor
        this.reportCreator = new ReportCreator(this.connection, this.fileOutput);
        
        
    } catch (Exception e) {
        System.out.println("Error initializing MenuSystem: " + e.getMessage());
        e.printStackTrace();
    }
}

    



/**
 * Displays the main menu.
 */
 private void displayMainMenu() {
        System.out.println("Welcome to the CMS system. To continue please log in.");
        System.out.println("Main Menu:");
        System.out.println("");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.println("");
    }

    
 /**
  * Gets the menu choice from the user.
  * 
  * @return the menu choice entered by the user
  */
 public int getMenuChoice() {
        int choice = 0;
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
            }
        }
        return choice;
    }

  /**
   * Closes the scanner.
   */
 public void closeScanner() {
        scanner.close();
    }

 
 /**
  * Runs the main menu.
  */
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
 

/**
  * Runs the entire menu system.
  */
 public void run() {
        runMainMenu();
        // Other menu logic goes here
    }

    
/**
 * Authenticates the user login. Determines what type of the user is logging in
 * and instantiates office and lecturer objects.
 * 
 * @param username the username entered by the user
 * @param password the password entered by the user
 * @return the user object if authentication is successful, otherwise null
 * @throws SQLException if a database error occurs
 */   
public Object authenticate(String username, String password) throws SQLException {
    // Check if admin
    if (username.equals(admin.getAdminUsername()) && password.equals(admin.getAdminPassword())) {
        return admin;
    } else {
        // Check if user exists in the login_details table
        String query = "SELECT lecturer_id FROM login_details WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Set parameters
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute query
            try (ResultSet resultSet = statement.executeQuery()) {
                // Check if any rows are returned
                if (resultSet.next()) {
                    int lecturerId = resultSet.getInt("lecturer_id");
                    if (resultSet.wasNull()) {
                        // If lecturer_id is null, user is an office user
                        office = new Office(username, password, "office_user", reportCreator, connection);
                       System.out.println("Office user initialized. Username: " + office.getUsername());
                        return office;
                    } else {
                        // If lecturer_id is not null, user is a lecturer
                        this.lecturerId = lecturerId;
                        lecturer = new Lecturer(username, password, "lecturer_user", lecturerId, reportCreator);
                        return lecturer;
                    }
                } else {
                    return null; // No rows returned, authentication failed
                }
            }
        } catch (SQLException e) {
            System.out.println("Error authenticating user: " + e.getMessage());
            return null;
        }
    }
}



/**
 * Processes the login procedure.
 */
public void processLogin() {
    
    System.out.println("Login:");
    System.out.println("Please enter Administrator, Lecturer or Office user credientials");
    System.out.println("Please note if a lecturer or office user has not been created, only the administrator account will be available");
    System.out.println("");
    System.out.print("Enter username: ");
    String username = scanner.next();
    System.out.print("Enter password: ");
    String password = scanner.next();
    System.out.println("");
    System.out.println("");

    try {
        // Authenticate the user
        Object user = authenticate(username, password);

        if (user instanceof Admin) {
            System.out.println("");
            System.out.println("Admin login successful!");
            System.out.println("");
            runAdminMenu();
        } else if (user instanceof Office) {
            System.out.println("Office login successful!");           
            System.out.println("Welcome " + username + "!");
            runOfficeUserMenu();
        } else if (user instanceof Lecturer) {
            System.out.println("Lecturer login successful!");
            System.out.println("Welcome " + username + "!");
            runLecturerMenu();
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    } catch (SQLException e) {
        System.out.println("Error authenticating user: " + e.getMessage());
    }
}




// Run admin menu
 public void runAdminMenu() {
    int choice;
    do {
        displayAdminMenu();
        choice = getMenuChoice();
        switch (choice) {
            case 1:
                System.out.print("Enter username for office user: ");
                String officeUsername = scanner.next();
                System.out.print("Enter password for office user: ");
                String officePassword = scanner.next();
                admin.createOfficeUser(officeUsername, officePassword);
                break;
                
            case 2:
                System.out.print("Enter username for lecturer: ");
                String lecturerUsername = scanner.next();
                System.out.print("Enter password for lecturer: ");
                String lecturerPassword = scanner.next();
                System.out.print("Enter lecturer ID: ");
                System.out.println("(For Sams eyes only: Lecturer ID is number between 1 and 100)");
                int lecturerId = scanner.nextInt();
                admin.createLecturerUser(lecturerUsername, lecturerPassword, lecturerId);
                break;
                
            case 3:
                System.out.print("Enter existing username to modify: ");
                String existingUsername = scanner.next();
                System.out.print("Enter new username: ");
                String newUsername = scanner.next();
                admin.modifyUsername(existingUsername, newUsername);
                break;
              
             case 4:
                System.out.print("Enter username of user who's password you want to modify: ");
                String username = scanner.next();
                System.out.print("Enter new password: ");
                String newPassword = scanner.next(); 
                admin.modifyUserPassword(username, newPassword); // Call the new method for modifying user password
                break;   
             
            case 5:
                System.out.print("Enter username of the lecturer who's role you want to modify: ");
                String lecturerusername = scanner.next();
                System.out.print("Enter new password: ");
                String newRole = scanner.next(); 
                admin.changeLecturerRole(lecturerusername, newRole); // Call the new method for modifying user password
                break;
                
            case 6:
                System.out.print("Enter username to delete: ");
                String userToDelete = scanner.next();
                admin.deleteUser(userToDelete);
                break;
                
            case 7:
                System.out.print("Enter new username for admin: ");
                String newAdminUsername = scanner.next();
                admin.setAdminUsername(newAdminUsername);
                break;
                
            case 8:
                System.out.print("Enter new password for admin: ");
                String newAdminPassword = scanner.next();
                admin.setAdminPassword(newAdminPassword);
                break;
                
            case 9:
                System.out.println("Logging out...");
                break;
                
                default:
                System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 9);
    }


    // Display admin menu
    public void displayAdminMenu() {
    System.out.println("Admin Menu:");
    System.out.println("");
    System.out.println("1. Create and add a new Office User");
    System.out.println("2. Create and add a new Lecturer User");
    System.out.println("3. Modify existing users username");
    System.out.println("4. Modify lecturer's role");
    System.out.println("5. Modify existing users password");
    System.out.println("6. Delete user");
    System.out.println("7. Change Administrator username");
    System.out.println("8. Change Administrators password");
    System.out.println("9. Logout");
}

   
    
// Run office user menu
public void runOfficeUserMenu() {
    
    int choice;
    do {
        displayOfficeUserMenu();
        choice = getMenuChoice();
        switch (choice) {
            case 1:
                System.out.print("Enter new username: ");
                String newUsername = scanner.next();
                office.changeOfficeUsername(newUsername, office); // Call method to change own username
                break;
            case 2:
                System.out.print("Enter new password: ");
                String newPassword = scanner.next();
                office.changeOfficePassword(newPassword, office); // Call method to change own password
                break;
            case 3:
                String courseReport = office.generateCourseReport(); // Generate the report content
                chooseReportOutputFormat(courseReport, "course_report", "course"); // Output the report
                break;
            case 4:
                System.out.print("Enter student ID: ");
                System.out.println("(For Sams eyes only: Student ID is a number between 1 and 300)");
                int studentId = scanner.nextInt();
                String studentReport = office.generateStudentReport(studentId); // Generate the student report
                chooseReportOutputFormat(studentReport, "student_report", "student"); // Output the report
                break;
            case 5:
                System.out.print("Enter lecturer ID: ");
                System.out.println("(For Sams eyes only: Lecturer ID is a number between 1 and 100)");
                int lecturerId = scanner.nextInt();
                String lecturerReport = office.generateLecturerReport(lecturerId); // Generate the lecturer report
                chooseReportOutputFormat(lecturerReport, "lecturer_report", "lecturer"); // Output the report
                break;
            case 6:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    } while (choice != 6);
}

    // Display office user menu
    public void displayOfficeUserMenu() {  
    System.out.println("Office User Menu:");
    System.out.println("");
    System.out.println("1. Change Own Username");
    System.out.println("2. Change Own Password");
    System.out.println("3. Generate Course Report");
    System.out.println("4. Generate Student Report");
    System.out.println("5. Generate Lecturer Report");
    System.out.println("6. Logout");
}
    
    

// Run lecturer menu
public void runLecturerMenu() {
    
    
    int choice;
    do {
        displayLecturerMenu();
        choice = getMenuChoice();
        switch (choice) {
            case 1:
                 String lecturerReport = lecturer.generatecurrentLecturerReport(lecturerId); // Call method to generate lecturer report
                 chooseReportOutputFormat(lecturerReport, "lecturer_report", "lecturer"); // Output the report
                 break;
            case 2:
                System.out.print("Enter new password: ");
                String newPassword = scanner.next();
                lecturer.changecurrentLecturerPassword(lecturerId, newPassword); // Call method to change lecturer password
                break;
            case 3:
                System.out.print("Enter new username: ");
                String newUsername = scanner.next();
                lecturer.changecurrentLecturerUsername(lecturerId, newUsername); // Call method to change lecturer username
                break;
            case 4:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    } while (choice != 4); // Correct placement of while loop condition
}
    
    // Display office user menu
    public void displayLecturerMenu() {
    System.out.println("Lecturer Menu:");
    System.out.println("");
    System.out.println("1. Generate your Lecturer Report");
    System.out.println("2. Change your Password");
    System.out.println("3. Change your Username");
    System.out.println("4. Logout");
}

    

/**
  * Prompts the user to choose the output format for a report and outputs the report accordingly.
  * 
  * @param reportContent the content of the report
  * @param reportName the name of the report
  * @param reportType the type of the report (e.g., course, student, lecturer)
  */
private void chooseReportOutputFormat(String reportContent, String reportName, String reportType) {
    System.out.println(" Please choose the output format for " + reportName + ":");
    System.out.println("1. Text (TXT)");
    System.out.println("2. CSV");
    System.out.println("3. Console");
    int formatChoice = getMenuChoice();
    String format = null;
    switch (formatChoice) {
        case 1:
            format = "txt";
            break;
        case 2:
            format = "csv";
            break;
        case 3:
            format = "console";
            break;
        default:
            System.out.println("Invalid choice. Using default format (TXT).");
            format = "txt";
            break;
    }
    // Pass the report type along with other parameters to the outputReport method
    fileOutput.outputReport(reportContent, reportName, format, reportType);
}
  
    
}




