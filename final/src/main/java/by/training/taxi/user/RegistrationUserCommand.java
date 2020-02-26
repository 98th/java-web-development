package by.training.taxi.user;

import by.training.taxi.bean.Bean;
import by.training.taxi.car.CarDto;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.contact.ContactDto;
import by.training.taxi.driver.DriverDto;

import by.training.taxi.util.Md5Util;
import by.training.taxi.util.RequestUtil;
import by.training.taxi.validator.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    private ContactValidator contactValidator;
    private CarValidator carValidator;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            ValidationResult userResult = userAccountValidator.validate(request);
            userResult.and(contactValidator.validate(request));
            if (!userResult.isValid() || !isLoginUnique(request)) {
                RequestUtil.setErrors(request, userResult);
                RequestUtil.forward(request, response, GET_USER_REGISTRATION);
                return;
            }
            Role role = Role.getRoleFromText(request.getParameter(PARAM_USER_ROLE)).orElse(CLIENT);
            String password = Md5Util.md5Apache(request.getParameter(PARAM_USER_PASSWORD));
            String login = request.getParameter(PARAM_USER_LOGIN);
            ContactDto contact = buildContact(request);
            UserAccountDto userAccountDto =  UserAccountDto.builder()
                    .login(login)
                    .password(password)
                    .role(role)
                    .isLocked(false)
                    .contact(contact)
                    .build();
            if (DRIVER == role) {
                ValidationResult driverResult = driverValidator.validate(request);
                driverResult.and(carValidator.validate(request));
                if (!driverResult.isValid()) {
                    RequestUtil.setErrors(request, driverResult);
                    RequestUtil.forward(request, response, GET_USER_REGISTRATION);
                    return;
                }
                DriverDto driverDto = buildDriver(request);
                userAccountDto.setDriver(driverDto);
            }
            long id = userAccountService.registerUser(userAccountDto);
            userAccountDto.setId(id);
            request.getSession().setAttribute(PARAM_USER, userAccountDto);
            request.getSession().setAttribute(PARAM_USER_ROLE, role);
            RequestUtil.sendRedirectToCommand(request, response, GET_USER_PROFILE_VIEW);
        } catch (UserServiceException e) {
            request.setAttribute(PARAM_ERROR, "error.saving.error");
            RequestUtil.forward(request, response, ERROR_VIEW);
        }
    }

    private DriverDto buildDriver(HttpServletRequest request) {
            String drivingLicence = request.getParameter(PARAM_DRIVER_LICENCE_NUM);
            String carColor = request.getParameter(PARAM_CAR_COLOR);
            String carModel = request.getParameter(PARAM_CAR_MODEL);
            String licencePlateNumber = request.getParameter(PARAM_CAR_LICENCE_PLATE_NUM);
            CarDto carDto = CarDto.builder()
                    .color(carColor)
                    .model(carModel)
                    .licencePlateNum(licencePlateNumber)
                    .build();
            return DriverDto.builder()
                    .drivingLicenceNum(drivingLicence)
                    .isWorking(false)
                    .car(carDto)
                    .build();
    }

    private ContactDto buildContact(HttpServletRequest request) {
        String email = request.getParameter(PARAM_USER_EMAIL);
        String firstName = request.getParameter(PARAM_USER_F_NAME);
        String lastName = request.getParameter(PARAM_USER_L_NAME);
        String phone = request.getParameter(PARAM_USER_PHONE);
        return ContactDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .build();

    }
    private boolean isLoginUnique(HttpServletRequest request) throws UserServiceException {
        boolean isLoginUnique = userAccountService.isLoginUnique(request.getParameter(PARAM_USER_LOGIN));
        if (!isLoginUnique) {
            request.setAttribute(PARAM_USER_LOGIN, "error.not.unique.login");
        }
        return isLoginUnique;
    }
}
