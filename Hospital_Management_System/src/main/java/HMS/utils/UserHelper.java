package HMS.utils;

import HMS.models.User;
import HMS.service.IUserService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class UserHelper implements IUserService {

    private static final String SALT = "ThisIsATopScret";
    private static UserHelper instance;

    // Private constructor to prevent instantiation
    private UserHelper() {
    }

    public static UserHelper getInstance() {
        if (instance == null) {
            instance = new UserHelper();
        }
        return instance;
    }

    @Override
    public boolean authenticate(User user, String inputPassword) {
        return verifyPassword(inputPassword, user.getHashedPassword());
    }

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

    @Override
    public boolean verifyPassword(String password, String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
    }
}
