package by.training.taxi.bean;

public class BeanRegistrationException extends RuntimeException {

    public BeanRegistrationException(String message) {
        super(message);
    }

    public BeanRegistrationException(String message, Exception e) {
        super(message, e);
    }
}