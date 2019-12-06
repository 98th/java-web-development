package by.training.taxi.validator;

import by.training.taxi.user.UserAccountService;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;

import static by.training.taxi.ApplicationConstants.*;

@Log4j
public class UserInfoValidator {
    private static final String PASSWORD_REGEX = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
    private static final String LOGIN_REGEX = "^[A-Za-z]([\\\\.A-Za-z0-9-]{1,18})([A-Za-z0-9])$";
    private static final String EMAIL_REGEX = "(\\\\w{5,})@(\\\\w+\\\\.)([a-z]{2,4})";
    private static final String PHONE_REGEX = "((29)|(44)|(33))(([1-9]{1}[0-9]{))";



    public ValidationResult validate(HttpServletRequest request) {
        ValidationResult validationResult = new ValidationResult();

        String login = request.getParameter(PARAM_USER_LOGIN);
        String password = request.getParameter(PARAM_USER_PASSWORD);
        String passwordRepeated = request.getParameter(PARAM_USER_PASSWORD_REPEATED);
        String email = request.getParameter(PARAM_USER_EMAIL);
        String phone =  request.getParameter(PARAM_USER_PHONE);

        if(login.isEmpty() || password.isEmpty() || passwordRepeated.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            validationResult.addMessage("Invalid data", "fields cannot be empty!");
        }

        if(!login.matches(LOGIN_REGEX)) {
            validationResult.addMessage(PARAM_USER_LOGIN, "invalid login");
        }
        if(!password.matches(PASSWORD_REGEX)) {
            validationResult.addMessage(PARAM_USER_PASSWORD, "invalid login");
        }
        if(!passwordRepeated.matches(PASSWORD_REGEX)) {
            validationResult.addMessage(PARAM_USER_PASSWORD_REPEATED, "invalid login");
        }
        if(!password.equals(passwordRepeated)) {
            validationResult.addMessage(PARAM_USER_PASSWORD, "password and repeat password fields must be identical");
        }
        if(!email.matches(EMAIL_REGEX)) {
            validationResult.addMessage(PARAM_USER_EMAIL, "invalid login");
        }
        if(!phone.matches(PHONE_REGEX)) {
            validationResult.addMessage(PARAM_USER_PHONE, "invalid login");
        }
        return validationResult;
    }
}
