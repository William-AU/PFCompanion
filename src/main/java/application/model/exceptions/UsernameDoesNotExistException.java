package application.model.exceptions;

public class UsernameDoesNotExistException extends Exception {
    public UsernameDoesNotExistException(String message) {
        super(message);
    }
}
