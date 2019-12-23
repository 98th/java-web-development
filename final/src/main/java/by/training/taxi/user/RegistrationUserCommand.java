package by.training.taxi.user;

import by.training.taxi.bean.Bean;
import by.training.taxi.car.CarDto;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.contact.ContactDto;
import by.training.taxi.discount.DiscountDto;
import by.training.taxi.driver.DriverDto;
import by.training.taxi.driver.DriverService;
import by.training.taxi.driver.DriverServiceException;
import by.training.taxi.role.Role;
import by.training.taxi.util.Md5Util;
import by.training.taxi.util.RequestUtil;
import by.training.taxi.validator.DriverDataValidator;
import by.training.taxi.validator.UserDataValidator;
import by.training.taxi.validator.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.taxi.ApplicationConstants.*;
import static by.training.taxi.role.Role.DRIVER;

@Bean(name=POST_USER_REGISTRATION)
@AllArgsConstructor
@Log4j
public class RegistrationUserCommand implements Command {
    private UserAccountService userAccountService;
    private DriverService driverService;


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            Validator userInfoValidator = new UserDataValidator();
            Validator driverInfoValidator = new DriverDataValidator();
            String login = request.getParameter(PARAM_USER_LOGIN);
            String email = request.getParameter(PARAM_USER_EMAIL);
            String password = Md5Util.md5Apache(request.getParameter(PARAM_USER_PASSWORD));
            String passwordRepeated = Md5Util.md5Apache(request.getParameter(PARAM_USER_PASSWORD_REPEATED));
            if (!passwordRepeated.equals(password)) {
                throw new CommandException();
            }
            String firstName = request.getParameter(PARAM_USER_F_NAME);
            String lastName = request.getParameter(PARAM_USER_L_NAME);
            String phone = request.getParameter(PARAM_USER_PHONE);
            Role role = Role.valueOf(request.getParameter(PARAM_USER_PHONE).toUpperCase());
            UserAccountDto userAccountDto = UserAccountDto.builder()
                    .login(login)
                    .password(password)
                    .role(role)
                    .build();
            ContactDto contactDto = ContactDto.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .phone(phone)
                    .build();
            DiscountDto discount = DiscountDto.builder()
                    .amount(0)
                    .build();
            userAccountDto.setDiscount(discount);
            userAccountDto.setContact(contactDto);
            userAccountService.registerUser(userAccountDto);
            if (role == DRIVER) {
                String drivingLicence = request.getParameter(PARAM_DRIVER_LICENCE_NUM);
                String carColor = request.getParameter("carColor");
                String carModel = request.getParameter("carModel");
                String licencePlateNumber = request.getParameter(PARAM_CAR_LICENCE_PLATE_NUM);
                DriverDto driverDto = DriverDto.builder()
                        .drivingLicenceNum(drivingLicence)
                        .build();
                userAccountDto.setDriver(driverDto);
                driverService.save(driverDto);
                CarDto carDto = CarDto.builder()
                        .color(carColor)
                        .model(carModel)
                        .licencePlateNum(licencePlateNumber)
                        .driverId(driverDto.getId())
                        .build();
                driverDto.setCar(carDto);
            }
            request.getSession().setAttribute(PARAM_USER, userAccountDto);
            request.setAttribute(VIEWNAME_REQ_PARAMETER, GET_USER_PAGE_VIEW);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/layout.jsp");
            requestDispatcher.forward(request, response);
        } catch (ServletException | IOException | UserServiceException | DriverServiceException e) {
            request.setAttribute("error", "error.saving_error");
            RequestUtil.forward(request, response, ERROR_VIEW);
        }
    }

}
