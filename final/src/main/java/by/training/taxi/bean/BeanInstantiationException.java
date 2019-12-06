package by.training.taxi.bean;

public class BeanInstantiationException extends BeanRegistrationException {
    public BeanInstantiationException(String message) {
        super(message);
    }

    public BeanInstantiationException(String message, Exception e) {
        super(message, e);
    }
}
