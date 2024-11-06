package HMS.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import HMS.enums.Gender;
import HMS.enums.Role;

public class TestingUser {
    private String userId;
    private String hashedPassword;
    private Gender gender;
    private String name;
    private Role role;

    private static final String DEFAULT_PASSWORD = "password";


    // Constructor
    public TestingUser(String userId, String password, Gender gender, String name, Role role) {
        this.userId = userId;
        this.hashedPassword = passwordHashing(password);
        this.gender = gender;
        this.name = name;
        this.role = role;

    }

    // Method to change password
    public void changePassword(String newPassword) {
        this.hashedPassword = passwordHashing(newPassword);
    }

    // Method to login
    public boolean login() {
        
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter User ID: ");
        String inputUserId = scanner.nextLine();

        // Check if the user ID matches
        if (!this.userId.equals(inputUserId)) {
            System.out.println("User ID not found.");
            return false;
        }

        // Check if the current password is the default password
        if (this.hashedPassword.equals(passwordHashing(DEFAULT_PASSWORD))) {
            System.out.println("First-time login detected. Please change your password.");

            // Prompt user to change password
            System.out.print("Enter New Password: ");
            String newPassword = scanner.nextLine();
            changePassword(newPassword);  // Set and hash the new password
            System.out.println("Password changed successfully!");
        }

        // Proceed with regular login after password change (if required)
        while (true) {
            System.out.print("Enter Password: ");
            String inputPassword = scanner.nextLine();

            // Hash the entered password for comparison
            String hashedInputPassword = passwordHashing(inputPassword);

            if (this.hashedPassword.equals(hashedInputPassword)) {
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Invalid password. Please try again.");
            }
        }
    }  

    // Method to logout
    public void logout() {
        System.out.println("User logged out.");
    }

    // Method to validate password
    public boolean validatePassword(String inputPassword) {
        String hashedInputPassword = passwordHashing(inputPassword);
        return this.hashedPassword.equals(hashedInputPassword);
    }

    // Method to display password without hashing (as per your request)
    private String passwordHashing(String password) {
    try {
        // Create SHA-256 MessageDigest instance
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // Perform the hashing on the password and store the result as bytes
        byte[] hashBytes = digest.digest(password.getBytes());

        // Convert the hash bytes to a Base64 encoded string for easier storage and display
        return Base64.getEncoder().encodeToString(hashBytes);
    } catch (NoSuchAlgorithmException e) {
        // If SHA-256 is not available, handle the exception
        System.out.println("Hashing algorithm not available.");
        return null;
    }
}

    // Getters and Setters (optional, depending on need)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
