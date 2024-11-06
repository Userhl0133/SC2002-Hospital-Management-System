package HMS.models;
import HMS.enums.*;

public class User {
    private String userId;
    private String password;
    private Gender gender;
    private String name;
    private Role role;

    // Constructor
    public User(String userId, String password, Gender gender, String name, Role role) {
        this.userId = userId;
        this.password = password;
        this.gender = gender;
        this.name = name;
        this.role = role;
    }

    // Method to change password
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    // Method to login
    public void login(String userId, String password) {
        if (this.userId == userId && this.password.equals(password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    // Method to logout
    public void logout() {
        System.out.println("User logged out.");
    }

    // Method to validate password
    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    // Method to display password without hashing (as per your request)
    public String passwordHashing() {
        return this.password; // Normally this would be hashed
    }

    // Getters and Setters (optional, depending on need)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}