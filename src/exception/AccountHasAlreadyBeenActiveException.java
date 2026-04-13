package exception;

public class AccountHasAlreadyBeenActiveException extends RuntimeException {
    public AccountHasAlreadyBeenActiveException(String message) {
        super(message);
    }
}
