package by.training.taxi.location;

public class LocationServiceException extends Exception {
    public LocationServiceException() {
    }

    public LocationServiceException(String message) {
        super(message);
    }

    public LocationServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocationServiceException(Throwable cause) {
        super(cause);
    }
}
