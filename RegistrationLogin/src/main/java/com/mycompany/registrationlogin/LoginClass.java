package com.mycompany.registrationlogin;

public class LoginClass {
    
    private String userStore;
    private String passStore;
    
    public boolean checkUserName(String username) {
        if (username == null) return false;
        return username.contains("_") && username.length() <= 5;
    }
    
    public boolean checkPasswordComplexity(String password) {
        if (password == null) return false;
        boolean length = password.length() >= 8;
        boolean caps = !password.equals(password.toLowerCase());
        boolean nums = password.matches(".*[0-9].*");
        boolean syms = password.matches(".*[!@#$%^&*()].*");
        
        return length && caps && nums && syms;
    }
    
    public boolean checkCellPhoneNumber(String cell) {
        if (cell == null) return false;
        return cell.startsWith("+") && cell.length() <= 13;
    }
    
    public String registerUser(String username, String password, String cell) {
        if (!checkUserName(username)) return "The username is incorrectly formatted.";
        if (!checkPasswordComplexity(password)) return "The password does not meet the complexity requirements.";
        if (!checkCellPhoneNumber(cell)) return "The phone number format is not correct.";

        this.userStore = username;
        this.passStore = password;
        
        return "The two above conditions have been met, and the user has been registered successfully.";
    }

    public boolean loginUser(String username, String password) {
        if (userStore == null || passStore == null) return false;
        return username.equals(userStore) && password.equals(passStore);
    }
    
    public String returnLoginStatus(boolean ok, String f, String l) {
        if (ok) {
            return "Welcome " + f + " " + l + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}