package exception;

public class AccountOriginEqualsDestinationException extends RuntimeException {
    public AccountOriginEqualsDestinationException(String message) {
        super(message);
    }
}
