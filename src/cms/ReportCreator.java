
package cms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ReportCreator {
    
 
    private DatabaseConnection dbConnector;
    private FileOutput fileOutput;

    public ReportCreator(DatabaseConnection dbConnector, FileOutput fileOutput) {
        this.dbConnector = dbConnector;
        this.fileOutput = fileOutput;
    }

    public void generateCourseReport() {
       try (Connection connection = dbConnector.establishConnection()) {
        // SQL query to fetch course report data
         String query = "SELECT m.module_name, " +
                               "c.course_name AS program_name, " +
                               "COUNT(DISTINCT e.student_id) AS num_students, " +
                               "CONCAT(l.lecturer_firstname, ' ', l.lecturer_lastname) AS lecturer_name, " +
                               "COALESCE(m.module_location, 'online') AS room_assignment " +
                       "FROM modules m " +
                       "INNER JOIN courses c ON m.course_id = c.course_id " +
                       "LEFT JOIN enrolments e ON m.module_id = e.module_id " +
                       "INNER JOIN lecturers l ON m.lecturer_id = l.lecturer_id " +
                       "GROUP BY m.module_name, c.course_name, lecturer_name, room_assignment";

              try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                  System.out.println("Generated Course Report:");
                  System.out.printf("%-70s %-50s %-20s %-40s %-20s\n", "Module Name", "Program", "Students Enrolled", "Lecturer", "Room");

                 while (resultSet.next()) {
                     String moduleName = resultSet.getString("module_name");
                     String programName = resultSet.getString("program_name");
                     int numStudents = resultSet.getInt("num_students");
                     String lecturerName = resultSet.getString("lecturer_name");
                     String roomAssignment = resultSet.getString("room_assignment");

                     System.out.printf("%-70s %-50s %-20d %-40s %-20s\n", moduleName, programName, numStudents, lecturerName, roomAssignment);
                    }
                   }
        
                 } catch (SQLException e) {
                  e.printStackTrace();
                 }
    }

    public void generateStudentReport() {
     // Method implementation
    int studentId = 1; // Replace with user input
    
    try (Connection connection = dbConnector.establishConnection()) {
        
         // SQL query to fetch student report data
         String query = "SELECT s.student_firstname, s.student_lastname, s.student_number, c.course_name AS program_name, " +
                         "GROUP_CONCAT(DISTINCT m.module_name) AS enrolled_modules, " +
                         "GROUP_CONCAT(DISTINCT CONCAT(m.module_name, ' (Grade: ', g.student_grade, ')')) AS completed_modules, " +
                         "GROUP_CONCAT(DISTINCT CASE WHEN g.module_to_be_repeated = 'false' THEN NULL " +
                                                  "ELSE CONCAT(m.module_name, ' (Grade: ', g.student_grade, ')') END) AS modules_to_repeat " +
                         "FROM students s " +
                         "INNER JOIN enrolments e ON s.student_id = e.student_id " +
                         "INNER JOIN modules m ON e.module_id = m.module_id " +
                         "INNER JOIN courses c ON m.course_id = c.course_id " +
                         "LEFT JOIN grades g ON s.student_id = g.student_id AND m.module_id = g.module_id " +
                         "WHERE s.student_id = ? " +
                         "GROUP BY s.student_firstname, s.student_lastname, s.student_number, program_name";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Set parameters
            statement.setInt(1, studentId); // studentId is set for now, will be retrieved from user input

            // Execute query
            try (ResultSet resultSet = statement.executeQuery()) {
                StringBuilder reportContent = new StringBuilder();
                reportContent.append("Student Report:\n\n");

                // Process the result set
                if (resultSet.next()) {
                    String studentName = resultSet.getString("student_firstname") + " " + resultSet.getString("student_lastname");
                    String studentNumber = resultSet.getString("student_number");
                    String programName = resultSet.getString("program_name");
                    String enrolledModules = resultSet.getString("enrolled_modules");
                    String completedModules = resultSet.getString("completed_modules");
                    String modulesToRepeat = resultSet.getString("modules_to_repeat");

                    reportContent.append("Student Name: ").append(studentName).append("\n");
                    reportContent.append("Student Number: ").append(studentNumber).append("\n");
                    reportContent.append("Program: ").append(programName).append("\n");
                    reportContent.append("Enrolled Modules: ").append(enrolledModules).append("\n");
                    reportContent.append("Completed Modules: ").append(completedModules).append("\n");

                 // Replace "null" with "None" for modules to repeat
                    if (modulesToRepeat == null) {
                        modulesToRepeat = "None";
                    }
                    reportContent.append("Modules to Repeat: ").append(modulesToRepeat).append("\n");
                } else {
                    reportContent.append("No data found for the specified student.");
                }

                // Print or save the report content
                System.out.println(reportContent.toString());
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    

    public void generateLecturerReport() {
        // Method implementation
    }
}