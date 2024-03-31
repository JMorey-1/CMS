
package cms;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ReportCreator {
    
 
    private DatabaseConnection connection;
    private FileOutput fileOutput;

    public ReportCreator(DatabaseConnection databaseConnection, FileOutput fileOutput) {
        this.connection = databaseConnection;
        this.fileOutput = fileOutput;
    }

public String generateCourseReport() {
       StringBuilder reportContent = new StringBuilder();
     
       try {
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

            reportContent.append("Course Report:\n\n");
            reportContent.append(String.format("%-65s %-50s %-20s %-20s %-10s\n",
                    "Module Name", "Program", "Students Enrolled", "Lecturer", "Room"));

            while (resultSet.next()) {
                String moduleName = resultSet.getString("module_name");
                String programName = resultSet.getString("program_name");
                int numStudents = resultSet.getInt("num_students");
                String lecturerName = resultSet.getString("lecturer_name");
                String roomAssignment = resultSet.getString("room_assignment");

                reportContent.append(String.format("%-65s %-50s %-20d %-20s %-10s\n",
                        moduleName, programName, numStudents, lecturerName, roomAssignment));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return reportContent.toString();
}


public String generateStudentReport(int studentId) {
    StringBuilder reportContent = new StringBuilder();
    
    try {
        // SQL query to fetch student report data
      String query = "SELECT s.student_firstname, s.student_lastname, s.student_number, c.course_name AS program_name, " +
               "GROUP_CONCAT(DISTINCT m.module_name) AS enrolled_modules, " +
               "GROUP_CONCAT(DISTINCT CONCAT(m.module_name, ' (Grade: ', g.student_grade, ')')) AS completed_modules, " +
               "CASE WHEN g.module_to_be_repeated = 'false' THEN 'None' ELSE m.module_name END AS modules_to_repeat " +
               "FROM students s " +
               "INNER JOIN enrolments e ON s.student_id = e.student_id " +
               "INNER JOIN modules m ON e.module_id = m.module_id " +
               "INNER JOIN courses c ON m.course_id = c.course_id " +
               "LEFT JOIN grades g ON s.student_id = g.student_id AND m.module_id = g.module_id " +
               "WHERE s.student_id = ? " +
               "GROUP BY s.student_firstname, s.student_lastname, s.student_number, program_name, modules_to_repeat";





        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the student ID parameter
            statement.setInt(1, studentId);

            // Execute the query
            try (ResultSet resultSet = statement.executeQuery()) {
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
                    reportContent.append("Modules to Repeat: ").append(modulesToRepeat).append("\n");
                } else {
                    reportContent.append("No data found for the specified student.");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return reportContent.toString();
}


public String generateLecturerReport(int lecturerId) {
    StringBuilder reportContent = new StringBuilder();
    System.out.println(lecturerId);
    try  {
        // SQL query to fetch lecturer report data
        String query = "SELECT l.lecturer_firstname, l.lecturer_lastname, l.lecturer_role, " +
                       "GROUP_CONCAT(DISTINCT CASE WHEN lm.semester_id = ? THEN m.module_name END) AS taught_modules_this_semester, " +
                       "COUNT(DISTINCT CASE WHEN lm.semester_id = ? THEN e.student_id END) AS num_students, " +
                       "GROUP_CONCAT(DISTINCT m.module_name) AS all_taught_modules " +
                       "FROM lecturers l " +
                       "INNER JOIN lecturer_modules lm ON l.lecturer_id = lm.lecturer_id " +
                       "INNER JOIN modules m ON lm.module_id = m.module_id " +
                       "LEFT JOIN enrolments e ON m.module_id = e.module_id " +
                       "WHERE l.lecturer_id = ? " +
                       "GROUP BY l.lecturer_firstname, l.lecturer_lastname, l.lecturer_role";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the semester ID parameter
            int currentSemesterId = 1;
            statement.setInt(1, currentSemesterId);
            statement.setInt(2, currentSemesterId);

            // Set the lecturer ID parameter
            statement.setInt(3, lecturerId);

            // Execute the query
            try (ResultSet resultSet = statement.executeQuery()) {
                reportContent.append("Lecturer Report:\n\n");

                // Process the result set
                if (resultSet.next()) {
                    String lecturerName = resultSet.getString("lecturer_firstname") + " " + resultSet.getString("lecturer_lastname");
                    String lecturerRole = resultSet.getString("lecturer_role");
                    String taughtModulesThisSemester = resultSet.getString("taught_modules_this_semester");
                    int numStudents = resultSet.getInt("num_students");
                    String allTaughtModules = resultSet.getString("all_taught_modules");

                    reportContent.append("Lecturer Name: ").append(lecturerName).append("\n");
                    reportContent.append("Lecturer Role: ").append(lecturerRole).append("\n");
                    reportContent.append("Modules Teaching This Semester: ").append(taughtModulesThisSemester).append("\n");
                    reportContent.append("Number of Students: ").append(numStudents).append("\n");
                    reportContent.append("All Modules Taught: ").append(allTaughtModules).append("\n");
                } else {
                    reportContent.append("No data found for the specified lecturer.");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return reportContent.toString();
}

}
