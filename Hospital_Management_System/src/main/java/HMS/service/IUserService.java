package HMS.service;

import HMS.models.User;

// Interface for User Service
public interface IUserService {
    // methods to authenticate user
    boolean authenticate(User user, String inputPassword);
    // methods to hash password
    String hashPassword(String password);
    // methods to verify password
    boolean verifyPassword(String password, String hashedPassword);
}
