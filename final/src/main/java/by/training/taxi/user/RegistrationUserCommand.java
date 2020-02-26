package by.training.taxi.user;

import by.training.taxi.bean.Bean;
import by.training.taxi.car.CarDto;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.contact.ContactDto;
import by.training.taxi.driver.DriverDto;

import by.training.taxi.util.LocationUtil;
import by.training.taxi.util.Md5Util;
import by.training.taxi.util.RequestUtil;
import by.training.taxi.validator.DriverValidator;
import by.training.taxi.validator.UserAccountValidator;
import by.training.taxi.validator.ValidationResult;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Map;

import static by.training.taxi.ApplicationConstants.*;
import static by.training.taxi.user.Role.CLIENT;
import static by.training.taxi.user.Role.DRIVER;

@Bean(name=POST_USER_REGISTRATION)
@AllArgsConstructor
@Log4j
public class RegistrationUserCommand implements Command {
    private UserAccountService userAccountService;
    private DriverValidator driverValidator;
    private UserAccountValidator userAccountValidator;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            ValidationResult userResult = userAccountValidator.validate(request);
            if (!userResult.isValid()) {
                setErrors(request, userResult);
                RequestUtil.forward(request, response, GET_USER_REGISTRATION);
                return;
            }
            String login = request.getParameter(PARAM_USER_LOGIN);
            String email = request.getParameter(PARAM_USER_EMAIL);
            String password = Md5Util.md5Apache(request.getParameter(PARAM_USER_PASSWORD));
            String firstName = request.getParameter(PARAM_USER_F_NAME);
            String lastName = request.getParameter(PARAM_USER_L_NAME);
            String phone = request.getParameter(PARAM_USER_PHONE);
            Role role = Role.getRoleFromText(request.getParameter(PARAM_USER_ROLE)).orElse(CLIENT);
            UserAccountDto userAccountDto = UserAccountDto.builder()
                    .login(login)
                    .password(password)
                    .role(role)
                    .isLocked(false)
                    .build();
            ContactDto contactDto = ContactDto.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .phone(phone)
                    .build();
            userAccountDto.setContact(contactDto);
            if (DRIVER == role) {
                String drivingLicence = request.getParameter(PARAM_DRIVER_LICENCE_NUM);
                String carColor = request.getParameter(PARAM_CAR_COLOR);
                String carModel = request.getParameter(PARAM_CAR_MODEL);
                String licencePlateNumber = request.getParameter(PARAM_CAR_LICENCE_PLATE_NUM);
                CarDto carDto = CarDto.builder()
                        .color(carColor)
                        .model(carModel)
                        .licencePlateNum(licencePlateNumber)
                        .build();
                DriverDto driverDto = DriverDto.builder()
                        .drivingLicenceNum(drivingLicence)
                        .isWorking(false)
                        .car(carDto)
                        .build();
                userAccountDto.setDriver(driverDto);
            }
            userAccountService.registerUser(userAccountDto);
            request.getSession().setAttribute(PARAM_USER, userAccountDto);
            request.getSession().setAttribute(PARAM_USER_ROLE, role);
            RequestUtil.sendRedirectToCommand(request, response, USER_PROFILE_CMD);
        } catch (UserServiceException e) {
            request.setAttribute(PARAM_ERROR, "error.saving_error");
            RequestUtil.forward(request, response, ERROR_VIEW);
        }
    }

    private boolean isLoginUnique(HttpServletRequest request) throws UserServiceException {
        boolean isLoginUnique = userAccountService.isLoginUnique(request.getParameter(PARAM_USER_LOGIN));
        if (!isLoginUnique) {
            request.setAttribute(PARAM_ERROR, "error.invalid.login");
        }
        return isLoginUnique;
    }

    private void setErrors(HttpServletRequest request, ValidationResult vr) {
        for (Map.Entry<String, String> result : vr.getResult().entrySet()) {
            request.setAttribute(result.getKey(), result.getValue());
        }
    }
}
