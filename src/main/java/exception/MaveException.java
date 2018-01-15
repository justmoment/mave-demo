package exception;

public class MaveException extends  RuntimeException {
    public MaveException() {
        super();
    }

    public MaveException(String message) {
        super(message);
    }

    public MaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public MaveException(Throwable cause) {
        super(cause);
    }

    protected MaveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
