package by.training.taxi.validator;

import by.training.taxi.bean.Bean;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;

import static by.training.taxi.ApplicationConstants.*;

import static by.training.taxi.util.ValidatorUtil.isStrEmpty;

@Log4j
@Bean(name=USER_ACCOUNT_VALIDATOR)
public class UserAccountValidator implements Validator {
    private static final String PASSWORD_REGEX = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
    private static final String LOGIN_REGEX = "^[A-Za-z]([\\\\.A-Za-z0-9-]{1,18})([A-Za-z0-9])$";

    public ValidationResult validate(HttpServletRequest request) {
        ValidationResult validationResult = new ValidationResult();

        String login = request.getParameter(PARAM_USER_LOGIN);
        String password = request.getParameter(PARAM_USER_PASSWORD);
        String passwordRepeated = request.getParameter(PARAM_USER_PASSWORD_REPEATED);

        if(isStrEmpty(login) || !login.matches(LOGIN_REGEX)) {
            validationResult.addMessage(PARAM_ERROR, "error.invalid.login");
        }

        if(isStrEmpty(password) || !passwordRepeated.matches(PASSWORD_REGEX)) {
            validationResult.addMessage(PARAM_ERROR, "error.invalid.password");
        }
        if(isStrEmpty(passwordRepeated) || !password.equals(passwordRepeated)) {
            validationResult.addMessage(PARAM_ERROR, "error.invalid.password.repeated");
        }
        return validationResult;
    }
}


