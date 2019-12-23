package by.training.taxi.discount;

public class DiscountServiceException extends Exception {
    public DiscountServiceException() {
    }

    public DiscountServiceException(String message) {
        super(message);
    }

    public DiscountServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiscountServiceException(Throwable cause) {
        super(cause);
    }
}
