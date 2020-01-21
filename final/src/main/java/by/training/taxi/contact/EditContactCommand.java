package by.training.taxi.contact;

import by.training.taxi.bean.Bean;
import by.training.taxi.car.CarDto;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.driver.DriverDto;
import by.training.taxi.driver.DriverService;
import by.training.taxi.driver.DriverServiceException;
import by.training.taxi.user.UserAccountDto;
import by.training.taxi.util.RequestUtil;
import by.training.taxi.validator.ContactValidator;
import by.training.taxi.validator.DriverValidator;
import by.training.taxi.validator.ValidationResult;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.training.taxi.ApplicationConstants.*;
import static by.training.taxi.user.Role.DRIVER;

@Bean(name = POST_EDIT_USER_INFO)
@AllArgsConstructor
public class EditContactCommand implements Command {
    private ContactService contactService;
    private DriverService driverService;
    private ContactValidator contactValidator;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            ValidationResult clientResult = contactValidator.validate(request);
            UserAccountDto userAccountDto = (UserAccountDto) request.getSession().getAttribute(PARAM_USER);
            long id = userAccountDto.getId();
            ContactDto contact = userAccountDto.getContact();
            String firstName = request.getParameter(PARAM_USER_F_NAME);
            String lastName = request.getParameter(PARAM_USER_L_NAME);
            String phone = request.getParameter(PARAM_USER_PHONE);
            String email = request.getParameter(PARAM_USER_EMAIL);
            if (clientResult.isValid() || contactService.isEmailUnique(email)) {
                contact = ContactDto.builder()
                        .userId(id)
                        .email(email)
                        .firstName(firstName)
                        .lastName(lastName)
                        .phone(phone)
                        .id(id)
                        .build();
                contactService.update(contact);
            }
            if (DRIVER == userAccountDto.getRole()) {
                driverService.updateWithInfo(buildDriver(request, response, userAccountDto, contact));
            }
            RequestUtil.sendRedirectToCommand(request, response, USER_PAGE_CMD);
        } catch (ContactServiceException | DriverServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }

    private DriverDto buildDriver(HttpServletRequest request, HttpServletResponse response, UserAccountDto user, ContactDto contact) {
        DriverValidator driverValidator = new DriverValidator();
        ValidationResult driverResult = driverValidator.validate(request);
        DriverDto driver = user.getDriver();
        CarDto car = driver.getCar();
        String drivingLicence = request.getParameter(PARAM_DRIVER_LICENCE_NUM);
        String carColor = request.getParameter(PARAM_CAR_COLOR);
        String carModel = request.getParameter(PARAM_CAR_MODEL);
        String licencePlateNumber = request.getParameter(PARAM_CAR_LICENCE_PLATE_NUM);
        CarDto.builder()
                .color(carColor)
                .model(carModel)
                .licencePlateNum(licencePlateNumber)
                .build();
        return DriverDto.builder()
                .contact(contact)
                .userId(user.getId())
                .car(car)
                .drivingLicenceNum(drivingLicence)
                .build();
    }
}