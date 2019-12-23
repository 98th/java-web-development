package by.training.taxi.driver;

public class DriverServiceException extends Exception {
    public DriverServiceException() {
    }

    public DriverServiceException(String message) {
        super(message);
    }

    public DriverServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DriverServiceException(Throwable cause) {
        super(cause);
    }
}
