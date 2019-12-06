package by.training.taxi.driver;

import by.training.taxi.user.ServiceException;

public class DriverServiceException extends ServiceException {
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
