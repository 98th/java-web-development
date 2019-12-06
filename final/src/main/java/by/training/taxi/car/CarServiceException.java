package by.training.taxi.car;

import by.training.taxi.user.ServiceException;

public class CarServiceException extends ServiceException {
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
