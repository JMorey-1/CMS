
package cms;




public class Office extends User {
   
    
        
        //attributes specific to Office
    
    
    
    // Constructor
      
    public Office(String username, String password, String userType) {
        // Call the constructor of the superclass (User) to initialize inherited attributes
        super(username, password, userType);
        
        // Initialize attributes specific to office
       
    }
    
    
    
    //methods specific to office
    //Can generate all types of reports
    //Can change their own username and password
}

    
    
    

