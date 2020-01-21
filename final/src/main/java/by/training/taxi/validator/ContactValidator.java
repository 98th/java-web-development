package by.training.taxi.validator;

import by.training.taxi.bean.Bean;

import javax.servlet.http.HttpServletRequest;

import static by.training.taxi.ApplicationConstants.*;
import static by.training.taxi.util.ValidatorUtil.isStrEmpty;

@Bean(name=CONTACT_VALIDATOR)
public class ContactValidator implements Validator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]{5,18}@[A-Za-z]{4,8}\\.[A-Za-z]{2,4}$";
    private static final String PHONE_REGEX = "(\\+)?(375)?(29|44|33|25)(([1-9]{1}[0-9]{6}))";

    @Override
    public ValidationResult validate(HttpServletRequest request) {
        ValidationResult validationResult = new ValidationResult();

        String email = request.getParameter(PARAM_USER_EMAIL);
        String phone =  request.getParameter(PARAM_USER_PHONE);

        if(isStrEmpty(email) || !email.matches(EMAIL_REGEX) ) {
            validationResult.addMessage(PARAM_ERROR, "error.invalid.email");
        }
        if(isStrEmpty(phone) || !phone.matches(PHONE_REGEX) ) {
            validationResult.addMessage(PARAM_ERROR, "error.invalid.phone");
        }
        return validationResult;
    }
}
