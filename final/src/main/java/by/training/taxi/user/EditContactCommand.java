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
import by.training.taxi.user.UserAccountDto;
import by.training.taxi.user.UserAccountService;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.taxi.ApplicationConstants.*;
import static by.training.taxi.role.Role.DRIVER;

@Bean(name = POST_EDIT_USER_INFO)
@AllArgsConstructor
public class EditContactCommand implements Command {
    private UserAccountService userAccountService;
    private ContactService contactService;
    private DriverService driverService;
    private CarService carService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ServletException, IOException {
        UserAccountDto userAccountDto = (UserAccountDto) request.getSession().getAttribute(PARAM_USER);
        ContactDto contact = userAccountDto.getContact();
        String firstName = request.getParameter(PARAM_USER_F_NAME);
        String lastName = request.getParameter(PARAM_USER_L_NAME);
        String phone = request.getParameter(PARAM_USER_PHONE);
        String email =request.getParameter(PARAM_USER_EMAIL);
        contact.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .build();
        if (userAccountDto.getRole() == DRIVER) {
            DriverDto driver = userAccountDto.getDriver();
            CarDto car = driver.getCarDto();
            String drivingLicence = request.getParameter("drivingLicence");
            String carColor = request.getParameter("carColor");
            String carModel = request.getParameter("carModel");
            String licencePlateNumber = request.getParameter("licencePlateNumber");
            car.builder()
                    .color(carColor)
                    .model(carModel)
                    .licencePlateNum(licencePlateNumber)
                    .build();
            driver.builder()
                    .drivingLicenceNum(drivingLicence)
                    .carDto(car)
                    .build();
        }
    }
}
