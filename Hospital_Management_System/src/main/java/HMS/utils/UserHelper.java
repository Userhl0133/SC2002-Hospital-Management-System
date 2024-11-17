package HMS.utils;

import HMS.models.User;
import HMS.service.IUserService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

// Helper class to handle user authentication and password hashing
public class UserHelper implements IUserService {

    // Salt value to add to the password before hashing
    private static final String SALT = "ThisIsATopScret";
    private static UserHelper instance;

    // Private constructor to prevent instantiation
    private UserHelper() {
    }

    // Singleton pattern to ensure only one instance of UserHelper is created
    public static UserHelper getInstance() {
        if (instance == null) {
            instance = new UserHelper();
        }
        return instance;
    }

    // Authenticate the user by comparing the input password with the stored hashed password
    @Override
    public boolean authenticate(User user, String inputPassword) {
        return verifyPassword(inputPassword, user.getHashedPassword());
    }

// Hash the password using the SHA-256 algorithm
    @Override
    public String hashPassword(String password) {
        try {
            // Create SHA-256 MessageDigest instance
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Combine the password with the hardcoded salt
            String saltedPassword = password + SALT;

            // Perform the hashing on the password and store the result as bytes
            byte[] hashBytes = digest.digest(saltedPassword.getBytes());

            // Convert the hash bytes to a Base64 encoded string for easier storage and display
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            // If SHA-256 is not available, handle the exception
            System.out.println("Hashing algorithm not available.");
            return null;
        }
    }

    // Verify the password by hashing the input password and comparing it with the stored hashed password
    @Override
    public boolean verifyPassword(String password, String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
    }
}
