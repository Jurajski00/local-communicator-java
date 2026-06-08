package project.exception;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String username) {
        super("User already exists: " + username);
    }
}
