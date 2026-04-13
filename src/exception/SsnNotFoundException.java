package exception;

public class SsnNotFoundException extends RuntimeException {
    public SsnNotFoundException(String message) {
        super(message);
    }
}
