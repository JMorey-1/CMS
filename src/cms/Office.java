
package cms;




public class Office extends User {
   
     private ReportCreator reportCreator;
        
      
      
    public Office(String username, String password, String userType, ReportCreator reportCreator) {
        // Call the constructor of the superclass (User)
        super(username, password, userType);
        this.reportCreator = reportCreator;
       
       
    }
    
  // Need to do properly with SQL QUERY
    // Method to change office user's OWN username
    public void changeOfficeUsername(String newUsername) {
        setUsername(newUsername);
    }
    
    // Need to do properly with SQL QUERY
    // Method to change office user's OWN password
    public void changeOfficePassword(String newPassword) {
        setPassword(newPassword);
    }
    
    
     // Method to generate a course report
    public String generateCourseReport() {
        return reportCreator.generateCourseReport();
    }
    
      // Method to generate a student report
    public String generateStudentReport(int studentId) {
        return reportCreator.generateStudentReport(studentId);
    }
    
      
    // Method to generate a lecturer report
    public String generateLecturerReport(int lecturerId) {
        return reportCreator.generateLecturerReport(lecturerId);
    }
    
}



    
    
    

