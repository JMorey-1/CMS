package cms.users;




public class User {
    
 
    // Attribute
    private String userName;
    private String password;
    private String userType;
    
    // Constructors
    public User(String username, String password, String userType) {
        this.userName = username;
        this.password = password;
        this.userType = userType;
    }
    
    // Getters and setters
    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

   public void setUserType(String userType) {
        this.userType = userType;
    }
    
 //METHODS   
    
    // Method change password
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
    
 
      
    }

    

