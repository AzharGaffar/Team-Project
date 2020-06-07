package sample;

public class LoginStorage {

    //Login Storage is used for storing the users login details after they log in.

    //Strings for storing.
    public static String FirstName;
    public static String LastName;
    public static String StaffNumber;
    public static Boolean firstLogin = false;
    public static String Username;
    public static String Role;

    //Constructor
    public LoginStorage() {
    }

    //Getters and Setters.
    public static String getFirstName() {
        return FirstName;
    }

    public static void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public static String getLastName() {
        return LastName;
    }

    public static void setLastName(String lastName) {
        LastName = lastName;
    }

    public static String getStaffNumber() {
        return StaffNumber;
    }

    public static void setStaffNumber(String staffNumber) {
        StaffNumber = staffNumber;
    }

    public static Boolean getFirstLogin() {
        return firstLogin;
    }

    public static void setFirstLogin(Boolean firstLogin) {
        LoginStorage.firstLogin = firstLogin;
    }

    public static String getUsername() {
        return Username;
    }

    public static void setUsername(String username) {
        Username = username;
    }

    public static void setRole(String role) {
        Role=role;
    }

    public static String getRole() {
        return Role;
    }

}