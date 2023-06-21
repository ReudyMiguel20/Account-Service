package account.exceptions;

public class BreachedPassword extends RuntimeException {

    public BreachedPassword() {
        super();
    }

    public BreachedPassword(String message) {
        super(message);
    }

    public BreachedPassword(String message, Throwable cause) {
        super(message, cause);
    }

    public BreachedPassword(Throwable cause) {
        super(cause);
    }

    public BreachedPassword(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
