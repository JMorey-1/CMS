
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
    }

    public void generateLecturerReport() {
        // Method implementation
    }
}