package by.training.taxi.util;

public class RequestUtilException extends  RuntimeException {
    public RequestUtilException() {
    }

    public RequestUtilException(String message) {
        super(message);
    }

    public RequestUtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestUtilException(Throwable cause) {
        super(cause);
    }
}
