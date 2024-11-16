package HMS.service;

import HMS.models.User;

public interface IUserService {
    boolean authenticate(User user, String inputPassword);
    String hashPassword(String password);
    boolean verifyPassword(String password, String hashedPassword);
}
