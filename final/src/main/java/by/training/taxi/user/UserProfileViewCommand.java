package by.training.taxi.user;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.contact.ContactDto;
import by.training.taxi.contact.ContactService;
import by.training.taxi.contact.ContactServiceException;
import by.training.taxi.discount.DiscountDto;
import by.training.taxi.discount.DiscountService;
import by.training.taxi.discount.DiscountServiceException;
import by.training.taxi.driver.DriverDto;
import by.training.taxi.driver.DriverService;
import by.training.taxi.driver.DriverServiceException;
import by.training.taxi.location.LocationDto;
import by.training.taxi.location.LocationService;
import by.training.taxi.location.LocationServiceException;
import by.training.taxi.util.RequestUtil;
import by.training.taxi.wallet.WalletDto;
import by.training.taxi.wallet.WalletService;
import by.training.taxi.wallet.WalletServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.training.taxi.ApplicationConstants.*;
import static by.training.taxi.user.Role.DRIVER;

@Bean(name=USER_PROFILE_CMD)
@AllArgsConstructor
@Log4j
public class UserProfileViewCommand implements Command {
    private DriverService driverService;
    private ContactService contactService;
    private LocationService locationService;
    private DiscountService discountService;
    private WalletService walletService;


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        UserAccountDto userAccountDto = (UserAccountDto) request.getSession().getAttribute(PARAM_USER);
        Long id = userAccountDto.getId();
        try {
            ContactDto contact = contactService.getByUserId(id);
            userAccountDto.setContact(contact);
            LocationDto location = locationService.getById(id);
            userAccountDto.setLocation(location);
            WalletDto wallet = walletService.getById(id);
            DiscountDto discount = discountService.getById(id);
            userAccountDto.setDiscount(discount);
            userAccountDto.setWallet(wallet);
            if (DRIVER == userAccountDto.getRole()) {
                //TODO
                DriverDto driver = driverService.getByUserId(id);
                userAccountDto.setDriver(driver);
            }
            request.getSession().setAttribute(PARAM_USER, userAccountDto);
            RequestUtil.forward(request, response, GET_USER_PROFILE_VIEW);
        } catch (WalletServiceException | ContactServiceException | LocationServiceException |
                DiscountServiceException | DriverServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
