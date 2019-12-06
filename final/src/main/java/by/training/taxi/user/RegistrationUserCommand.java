package by.training.taxi.user;

import by.training.taxi.bean.Bean;
import by.training.taxi.car.CarDto;
import by.training.taxi.car.CarService;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.contact.ContactDto;
import by.training.taxi.contact.ContactService;
import by.training.taxi.driver.DriverDto;
import by.training.taxi.driver.DriverService;
import by.training.taxi.role.Role;
import by.training.taxi.util.Md5Util;
import by.training.taxi.wallet.WalletService;
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
    private ContactService contactService;
    private DriverService driverService;
    private CarService carService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ServletException, IOException {
        try {
            String login = request.getParameter(PARAM_USER_LOGIN);
            String email = request.getParameter("email");
            String password = Md5Util.md5Apache(request.getParameter(PARAM_USER_PASSWORD));
            String passwordRepeated = Md5Util.md5Apache(request.getParameter(PARAM_USER_PASSWORD_REPEATED));
            if (!passwordRepeated.equals(password)) {
                throw new CommandException();
            }
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String phone = request.getParameter("phone");
            Role role = Role.valueOf(request.getParameter("role").toUpperCase());
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
            userAccountService.registerUser(userAccountDto);
            userAccountDto.setContact(contactDto);
            if (role == DRIVER) {
                String drivingLicence = request.getParameter("drivingLicence");
                String carColor = request.getParameter("carColor");
                String carModel = request.getParameter("carModel");
                String licencePlateNumber = request.getParameter("licencePlateNumber");
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
                driverDto.setCarDto(carDto);
            }
            request.getSession().setAttribute(PARAM_USER, userAccountDto);
            request.setAttribute(VIEWNAME_REQ_PARAMETER, GET_USER_PAGE_VIEW);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/layout.jsp");
            requestDispatcher.forward(request, response);
        }catch (ServiceException e) {
            request.setAttribute("error", "saving_error");
            request.setAttribute(VIEWNAME_REQ_PARAMETER, "error");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/layout.jsp");
            requestDispatcher.forward(request, response);
        }
    }

}
