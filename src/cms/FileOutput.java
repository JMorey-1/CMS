
package cms;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.io.BufferedWriter;
import cms.users.User;
import cms.users.Admin;
import cms.users.Office;
import cms.users.Lecturer;


public class FileOutput {
    
 
public void outputReport(String reportContent, String fileName, String format, String reportType) {
    switch (format.toLowerCase()) {
        case "txt":
            writeTextFile(reportContent, fileName + ".txt");
            break;
        case "csv":
            // Pass the report type to the writeCSVFile method
            writeCSVFile(reportContent, fileName + ".csv", reportType);
            break;
        case "console":
            System.out.println("Report Content:\n" + reportContent);
            break;
        default:
            System.out.println("Invalid file type specified.");
            break;
    }
}

 
 
private void writeTextFile(String reportContent, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(reportContent);
            System.out.println("Report successfully written to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while writing the report to " + fileName);
            e.printStackTrace();
        }
    }
    
private void writeCSVFile(String reportContent, String fileName, String reportType) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        switch (reportType.toLowerCase()) {
            case "lecturer":
                writeLecturerCSV(reportContent, writer);
                break;
            case "student":
                writeStudentCSV(reportContent, writer);
                break;
            case "course":
                writeCourseCSV(reportContent, writer);
                break;
            default:
                System.out.println("Invalid report type specified.");
                return;
        }
        System.out.println("Report successfully written to " + fileName);
    } catch (IOException e) {
        System.out.println("An error occurred while writing the report to " + fileName);
        e.printStackTrace();
    }
}

  private void writeLecturerCSV(String reportContent, BufferedWriter writer) throws IOException {
    // Write CSV header for lecturer report
    writer.write("Lecturer Name,Lecturer Role,Modules Teaching This Semester,Number of Students,All Modules Taught");
    writer.newLine();

    // Split report content into lines
    String[] lines = reportContent.split("\n");

    // Extract values from each line
    String lecturerName = "";
    String lecturerRole = "";
    String taughtModulesThisSemester = "";
    String numStudents = "";
    String allTaughtModules = "";

    for (String line : lines) {
        String[] values = line.split(":");
        if (values.length == 2) {
            String key = values[0].trim();
            String value = values[1].trim();

            switch (key) {
                case "Lecturer Name":
                    lecturerName = value;
                    break;
                case "Lecturer Role":
                    lecturerRole = value;
                    break;
                case "Modules Teaching This Semester":
                    taughtModulesThisSemester = value;
                    break;
                case "Number of Students":
                    numStudents = value;
                    break;
                case "All Modules Taught":
                    allTaughtModules = value;
                    break;
            }
        }
    }

    // Write values to the CSV file
    writer.write(lecturerName + "," + lecturerRole + "," + taughtModulesThisSemester + "," + numStudents + "," + allTaughtModules);
    writer.newLine();
}

  private void writeStudentCSV(String reportContent, BufferedWriter writer) throws IOException {
    // Write CSV header for student report
    writer.write("Student Name,Student Number,Program,Enrolled Modules,Completed Modules,Modules to Repeat");
    writer.newLine();

    // Split report content into lines
    String[] lines = reportContent.split("\n");

    // Extract values from each line
    String studentName = "";
    String studentNumber = "";
    String program = "";
    String enrolledModules = "";
    String completedModules = "";
    String modulesToRepeat = "";

    for (String line : lines) {
        String[] values = line.split(":");
        if (values.length == 2) {
            String key = values[0].trim();
            String value = values[1].trim();

            switch (key) {
                case "Student Name":
                    studentName = value;
                    break;
                case "Student Number":
                    studentNumber = value;
                    break;
                case "Program":
                    program = value;
                    break;
                case "Enrolled Modules":
                    enrolledModules = value;
                    break;
                case "Completed Modules":
                    completedModules = value;
                    break;
                case "Modules to Repeat":
                    modulesToRepeat = value;
                    break;
            }
        }
    }

    // Write values to the CSV file
    writer.write(studentName + "," + studentNumber + "," + program + "," + enrolledModules + "," + completedModules + "," + modulesToRepeat);
    writer.newLine();
}

  private void writeCourseCSV(String reportContent, BufferedWriter writer) throws IOException {
    // Write CSV header for course report
    writer.write("Module Name,Program,Students Enrolled,Lecturer,Room");
    writer.newLine();

    // Split the report content into lines
    String[] lines = reportContent.split("\n");

    // Iterate over each line and extract values
    for (String line : lines) {
        // Split the line based on multiple spaces
        String[] values = line.split("\\s{2,}");

        // Ensure we have enough values to process
        if (values.length >= 5) {
            String moduleName = values[0].trim();
            String program = values[1].trim();
            String studentsEnrolled = values[2].trim();
            String lecturer = values[3].trim();
            String room = values[4].trim();

            // Write values to the CSV file
            writer.write(moduleName + "," + program + "," + studentsEnrolled + "," + lecturer + "," + room);
            writer.newLine();
        }
    }
}

}


      

