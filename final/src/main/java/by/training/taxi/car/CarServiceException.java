package by.training.taxi.car;


public class CarServiceException extends Exception {
    public CarServiceException() {
        super();
    }

    public CarServiceException(String message) {
        super(message);
    }

    public CarServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CarServiceException(Throwable cause) {
        super(cause);
    }
}
