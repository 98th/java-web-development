package by.training.taxi.validator;

import by.training.taxi.bean.Bean;

import javax.servlet.http.HttpServletRequest;

import static by.training.taxi.ApplicationConstants.*;
import static by.training.taxi.util.ValidatorUtil.isStrEmpty;

@Bean(name=CONTACT_VALIDATOR)
public class ContactValidator implements Validator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]{5,18}@[A-Za-z]{4,8}\\.[A-Za-z]{2,4}$";
    private static final String PHONE_REGEX = "(\\+)?(375)?(29|44|33|25)(([1-9]{1}[0-9]{6}))";
    private static final String NAME_REGEX = "^[A-Za-zА-Яа-я]{1,20}$";

    @Override
    public ValidationResult validate(HttpServletRequest request) {
        ValidationResult validationResult = new ValidationResult();

        String email = request.getParameter(PARAM_USER_EMAIL);
        String phone =  request.getParameter(PARAM_USER_PHONE);
        String firstName = request.getParameter(PARAM_USER_F_NAME);
        String lastName = request.getParameter(PARAM_USER_L_NAME);

        if(isStrEmpty(firstName) || isStrEmpty(lastName) || isStrEmpty(phone) || isStrEmpty(email)) {
            validationResult.addMessage(PARAM_ERROR, "error.empty.fields");
        }
        if(!firstName.matches(NAME_REGEX) ) {
            validationResult.addMessage(PARAM_USER_F_NAME, "error.invalid.f.name");
        }
        if(!lastName.matches(NAME_REGEX) ) {
            validationResult.addMessage(PARAM_USER_L_NAME, "error.invalid.l.name");
        }
        if(!email.matches(EMAIL_REGEX) ) {
            validationResult.addMessage(PARAM_USER_EMAIL, "error.invalid.email");
        }
        if(!phone.matches(PHONE_REGEX) ) {
            validationResult.addMessage(PARAM_USER_PHONE, "error.invalid.phone");
        }
        return validationResult;
    }
}
