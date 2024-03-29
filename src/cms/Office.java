
package cms;




public class Office extends User {
   
    private ReportCreator reportCreator;
    private FileOutput fileOutput;  
      
    public Office(String username, String password, String userType, ReportCreator reportCreator) {
        // Call the constructor of the superclass (User)
        super(username, password, userType);
        this.reportCreator = reportCreator;
        this.fileOutput = fileOutput;
    }
    
     
    
    // Method to change office user's OWN username
    public void changeOfficeUsername(String newUsername) {
        setUsername(newUsername);
    }
    
    // Method to change office user's OWN password
    public void changeOfficePassword(String newPassword) {
        setPassword(newPassword);
    }
    
    // Method to generate a course report
    public String generateCourseReport() {
        System.out.println("test test test");
        if (reportCreator != null) {
            return reportCreator.generateCourseReport();
        } else {
            return "Report creator is not initialized.";
        }
    }
    
    // Method to generate a student report
    public String generateStudentReport(int studentId) {
        System.out.println("test test test");
        if (reportCreator != null) {
            return reportCreator.generateStudentReport(studentId);
        } else {
            return "Report creator is not initialized.";
        }
    }
    
    // Method to generate a lecturer report
    public String generateLecturerReport(int lecturerId) {
        System.out.println("test test test");
        if (reportCreator != null) {
            return reportCreator.generateLecturerReport(lecturerId);
        } else {
            return "Report creator is not initialized.";
        }
    }
    
}


    
    
    

