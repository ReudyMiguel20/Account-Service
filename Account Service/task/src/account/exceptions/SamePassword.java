package account.exceptions;

public class SamePassword extends RuntimeException {

    public SamePassword() {
        super();
    }

    public SamePassword(String message) {
        super(message);
    }

    public SamePassword(String message, Throwable cause) {
        super(message, cause);
    }

    public SamePassword(Throwable cause) {
        super(cause);
    }

    public SamePassword(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
