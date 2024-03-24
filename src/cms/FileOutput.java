
package cms;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.io.BufferedWriter;



public class FileOutput {
    
 
 
    public void outputReport(String reportContent, String fileName, String fileType) {
        switch (fileType.toLowerCase()) {
            case "txt":
                writeTextFile(reportContent, fileName + ".txt");
                break;
            case "csv":
                writeCSVFile(reportContent, fileName + ".csv");
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

    private void writeCSVFile(String reportContent, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write CSV header if needed
            // writer.write("Header1,Header2,Header3\n");

            // Write report content
            writer.write(reportContent);
            System.out.println("Report successfully written to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while writing the report to " + fileName);
            e.printStackTrace();
        }
    }
}


      

