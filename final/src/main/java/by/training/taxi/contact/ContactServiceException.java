package by.training.taxi.contact;

import by.training.taxi.user.ServiceException;

public class ContactServiceException extends ServiceException {
    public ContactServiceException() {
        super();
    }

    public ContactServiceException(String message) {
        super(message);
    }

    public ContactServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContactServiceException(Throwable cause) {
        super(cause);
    }
}
