package by.training.taxi.validator;

import by.training.taxi.bean.Bean;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;

import static by.training.taxi.ApplicationConstants.*;

import static by.training.taxi.util.ValidatorUtil.isStrEmpty;

@Log4j
@Bean
public class UserDataValidator implements Validator {
    private static final String PASSWORD_REGEX = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
    private static final String LOGIN_REGEX = "^[A-Za-z]([\\\\.A-Za-z0-9-]{1,18})([A-Za-z0-9])$";
    private static final String EMAIL_REGEX = "(\\\\w{5,})@(\\\\w+\\\\.)([a-z]{2,4})";
    private static final String PHONE_REGEX = "(\\+)?((29)|(44)|(33))(([1-9]{1}[0-9]{7}))";



    public ValidationResult validate(HttpServletRequest request) {
        ValidationResult validationResult = new ValidationResult();

        String login = request.getParameter(PARAM_USER_LOGIN);
        String password = request.getParameter(PARAM_USER_PASSWORD);
        String passwordRepeated = request.getParameter(PARAM_USER_PASSWORD_REPEATED);
        String email = request.getParameter(PARAM_USER_EMAIL);
        String phone =  request.getParameter(PARAM_USER_PHONE);

        if(isStrEmpty(login) || !login.matches(LOGIN_REGEX)) {
            validationResult.addMessage(PARAM_USER_LOGIN, "Incorrect login");
        }

        if(isStrEmpty(password) || !passwordRepeated.matches(PASSWORD_REGEX)) {
            validationResult.addMessage(PARAM_USER_PASSWORD, "Incorrect password. There must be min 8" +
                    " symbols including capital letters");
        }
        if(isStrEmpty(passwordRepeated) || !password.equals(passwordRepeated)) {
            validationResult.addMessage(PARAM_USER_PASSWORD_REPEATED, "password and repeat password fields must be identical");
        }
        if(isStrEmpty(email) || !email.matches(EMAIL_REGEX) ) {
            validationResult.addMessage(PARAM_USER_EMAIL, "Incorrect email");
        }
        if(isStrEmpty(phone) || !phone.matches(PHONE_REGEX) ) {
            validationResult.addMessage(PARAM_USER_PHONE, "Incorrect phone");
        }
        return validationResult;
    }
}


