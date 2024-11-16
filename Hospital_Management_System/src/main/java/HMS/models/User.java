package HMS.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

import HMS.enums.Gender;
import HMS.enums.Role;

import HMS.service.IUserService;
import HMS.utils.UserHelper;

// Hashing and Login System
public abstract class User {

    private String userId;
    private String hashedPassword;
    private Gender gender;
    private String name;
    private Role role;

    private static final String DEFAULT_PASSWORD = "password";
    private static final IUserService userService = UserHelper.getInstance();


    public User() {
    }

    // Constructor
    public User(String userId, String password, Gender gender, String name, Role role) {
        this.userId = userId;
        this.hashedPassword = password;
        this.gender = gender;
        this.name = name;
        this.role = role;
    }

    // Method to change password
    public void changePassword(String newPassword) {
        this.hashedPassword = userService.hashPassword(newPassword);
    }

    public static User login(List<Patient> patients, List<Doctor> doctors, List<Pharmacist> pharmacists, List<Administrator> administrators, IUserService userService) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Hospital ID: ");
        String inputUserId = scanner.nextLine();

        // Search for the user in all lists
        User foundUser = findUserById(inputUserId, patients, doctors, pharmacists, administrators);

        // If no user is found, return null
        if (foundUser == null) {
            System.out.println("User not found.");
            return null;
        }

        // Check if it's a first-time login (default password)
        if (foundUser.hashedPassword.equals(userService.hashPassword(DEFAULT_PASSWORD))) {
            System.out.println("First-time login detected. Please change your password.");
            System.out.print("Enter New Password: ");
            String newPassword = scanner.nextLine();
            foundUser.changePassword(newPassword);
            System.out.println("Password changed successfully!");
        }

        // Validate the password
        while (true) {
            System.out.print("Enter Password: ");
            String inputPassword = scanner.nextLine().trim();
            String hashedInputPassword = userService.hashPassword(inputPassword);

            if (foundUser.hashedPassword.equals(hashedInputPassword)) {
                System.out.println("\nWelcome " + foundUser.getName() + " [" + foundUser.getRole() + "]");
                return foundUser;
            } else {
                System.out.println("Invalid password. Please try again.");
            }
        }
    }

// Method to find a user by userId from different lists
    public static User findUserById(String userId, List<Patient> patients, List<Doctor> doctors, List<Pharmacist> pharmacists, List<Administrator> administrators) {
        for (Patient patient : patients) {
            if (patient.getUserId().equals(userId)) {
                return patient;
            }
        }
        for (Doctor doctor : doctors) {
            if (doctor.getUserId().equals(userId)) {
                return doctor;
            }
        }
        for (Pharmacist pharmacist : pharmacists) {
            if (pharmacist.getUserId().equals(userId)) {
                return pharmacist;
            }
        }
        for (Administrator administrator : administrators) {
            if (administrator.getUserId().equals(userId)) {
                return administrator;
            }
        }
        return null; // User not found
    }

    // Method to logout
    public void logout() {
        System.out.println("User logged out.");
    }

    public abstract void showMenu();

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

    public String getHashedPassword() {
        return hashedPassword;
    }
}
