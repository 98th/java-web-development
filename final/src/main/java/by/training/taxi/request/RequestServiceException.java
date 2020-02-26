package by.training.taxi.request;

public class RequestServiceException extends Exception {
    public RequestServiceException() {
    }

    public RequestServiceException(String message) {
        super(message);
    }

    public RequestServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestServiceException(Throwable cause) {
        super(cause);
    }
}
