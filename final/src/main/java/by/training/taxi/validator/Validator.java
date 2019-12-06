package by.training.taxi.validator;

import javax.servlet.http.HttpServletRequest;

public interface Validator {
    ValidationResult validate (HttpServletRequest req);
}
