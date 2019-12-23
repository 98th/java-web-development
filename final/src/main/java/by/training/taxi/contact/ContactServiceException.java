package by.training.taxi.contact;


public class ContactServiceException extends Exception {
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
