
package cms;






public class Admin extends User {
   
    //attributes specific to Admin
    
        

// Constructor
      
    public Admin(String username, String password, String userType) {
        // Call the constructor of the superclass (User) to initialize inherited attributes
        super(username, password, userType);
        
        // Initialize attributes specific to Admin
       
    }
    
    
    
    
    
    
    
    //methods specific to Admin
      //The only user available at the start
      //Username: admin■ Password: java
      //Can add, modify and delete other users (including username, password and role)
      // Can change their own username and password
       //Cannot generate reports
    
    
}
