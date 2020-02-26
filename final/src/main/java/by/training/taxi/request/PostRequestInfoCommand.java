package by.training.taxi.request;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.discount.DiscountDto;
import by.training.taxi.discount.DiscountService;
import by.training.taxi.discount.DiscountServiceException;
import by.training.taxi.driver.DriverDto;
import by.training.taxi.driver.DriverService;
import by.training.taxi.driver.DriverServiceException;
import by.training.taxi.location.LocationDto;
import by.training.taxi.location.LocationService;
import by.training.taxi.location.LocationServiceException;
import by.training.taxi.user.UserAccountDto;
import by.training.taxi.util.LocationUtil;
import by.training.taxi.util.RequestUtil;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.util.Date;

import static by.training.taxi.ApplicationConstants.*;
import static by.training.taxi.ApplicationConstants.GET_REQUEST_INFO_VIEW;
import static java.lang.Math.*;
import static java.lang.Math.cos;

@AllArgsConstructor
@Bean(name = POST_REQUEST_INFO)
public class PostRequestInfoCommand implements Command {
    private DiscountService discountService;
    private DriverService driverService;
    private LocationService locationService;
    private RequestService requestService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            UserAccountDto user = (UserAccountDto) request.getSession().getAttribute(PARAM_USER);
            long userId = user.getId();
            String pickLocation = LocationUtil.split(user.getLocation().getLatitude(),user.getLocation().getLongitude());
            long carId = Long.parseLong(request.getParameter("carId"));
            DriverDto driverDto = driverService.getByCarId(carId);
            double distance = calculateDistance(user, driverDto);
            int waitingTime = calculateWaitingTime(distance);
            String price = calculatePrice(distance, user.getId());
            RequestDto requestDto = RequestDto.builder()
                    .clientId(userId)
                    .pickLocation(pickLocation)
                    .dropLocation(LocationUtil.getRandomLocation())
                    .requestStatus(RequestStatus.OFFERED)
                    .requestDate(new Date())
                    .price(new BigDecimal(price))
                    .driverId(driverDto.getUserId())
                    .build();
            long requestId = requestService.registerRequest(requestDto);
            request.getSession().setAttribute(PARAM_REQUEST_ID, requestId);
            request.getSession().setAttribute("waitingTime", waitingTime);
            request.getSession().setAttribute("price", price);
            RequestUtil.sendRedirectToCommand(request, response, GET_REQUEST_INFO_VIEW);
        } catch (LocationServiceException | RequestServiceException | DriverServiceException | DiscountServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }


    private double calculateDistance(UserAccountDto user, DriverDto driver) throws LocationServiceException {
        double latClient = user.getLocation().getLatitude();
        double longClient = user.getLocation().getLongitude();
        LocationDto location = locationService.getById(driver.getUserId());
        double latDriver = location.getLatitude();
        double longDriver = location.getLongitude();
        final double R = 6378.1;
        double sin1 = sin((latClient - latDriver) / 2);
        double sin2 = sin((longClient - longDriver) / 2);
        return 2 * R * asin(sqrt(sin1 * sin1 + sin2 * sin2 * cos(latClient) * cos(latDriver)));
    }

    private String calculatePrice(double distance, long userId) throws DiscountServiceException {
        DiscountDto discount = discountService.getById(userId);
        double output = 3 + distance * 0.5 * (1 - discount.getAmount() * 0.01);
        return String.format("%.2f", output).replace(',', '.');
    }

    private int calculateWaitingTime(double distance) {
        return 6 * (int) distance / 7;
    }
}