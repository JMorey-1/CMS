
package cms;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;


/**
 * Provides functionality to output reports to different file formats or to the console.
 * Supports output in TXT and CSV formats.
 */
public class FileOutput {
    
/**
 * Outputs the report content to the specified file format.
 * Supports TXT and CSV formats. If the format is "console", the report is printed to the console.
 *
 * @param reportContent The content of the report to be output.
 * @param fileName      The name of the file to which the report will be written.
 * @param format        The format in which the report will be written (TXT, CSV, or console).
 * @param reportType    The type of report being generated (lecturer, student, or course).
 */
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

/**
 * Writes the report content to a text file with the specified file name.
 *
 * @param reportContent The content of the report to be written.
 * @param fileName      The name of the text file to which the report will be written.
 */ 
private void writeTextFile(String reportContent, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(reportContent);
            System.out.println("Report successfully written to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while writing the report to " + fileName);
            e.printStackTrace();
        }
    }
/**
 * Sorting method that determines the type of report to be written and then passes
 * it to the appropriate sub method, each sub method tweaked for particular file layout.
 *
 * @param reportContent The content of the report to be written.
 * @param fileName      The name of the CSV file to which the report will be written.
 * @param reportType    The type of report being written (lecturer, student, or course).
 */    
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

 /**
  * Reorganizes lecturer report content and writes it to a CSV file using the provided BufferedWriter.
  *
  * @param reportContent The content of the lecturer report to be written.
  * @param writer        The BufferedWriter used to write to the CSV file.
  * @throws IOException If an I/O error occurs while writing to the file.
  */
  private void writeLecturerCSV(String reportContent, BufferedWriter writer) throws IOException {
    // CSV headers for lecturer report
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

/**
 * Reorganizes the student report content and writes to a CSV file using the provided BufferedWriter.
 *
 * @param reportContent The content of the student report to be written.
 * @param writer        The BufferedWriter used to write to the CSV file.
 * @throws IOException If an I/O error occurs while writing to the file.
 */
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

  
/**
 * Reorganizes course report and writes content to a CSV file using the provided BufferedWriter.
 *
 * @param reportContent The content of the course report to be written.
 * @param writer        The BufferedWriter used to write to the CSV file.
 * @throws IOException If an I/O error occurs while writing to the file.
 */
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


      

