package by.training.taxi.request;

import by.training.taxi.bean.Bean;
import by.training.taxi.car.CarDto;
import by.training.taxi.car.CarService;
import by.training.taxi.car.CarServiceException;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.discount.DiscountDto;
import by.training.taxi.discount.DiscountService;
import by.training.taxi.discount.DiscountServiceException;
import by.training.taxi.driver.DriverDto;
import by.training.taxi.driver.DriverService;
import by.training.taxi.driver.DriverServiceException;
import by.training.taxi.user.UserAccountDto;
import by.training.taxi.util.LocationUtil;
import by.training.taxi.util.RequestUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static by.training.taxi.ApplicationConstants.*;
import static java.lang.Math.*;

@Bean(name=POST_CAR_REQUIREMENT)
@AllArgsConstructor
@Log4j
public class FindCarCommand implements Command {
    private RequestService requestService;
    private DriverService driverService;
    private CarService carService;
    private DiscountService discountService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String requirement = request.getParameter("requirement");
        UserAccountDto user = (UserAccountDto) request.getSession().getAttribute(PARAM_USER);
        long userId = user.getId();
        RequestDto requestDto = new RequestDto();
        requestDto.setClientId(userId);
        requestDto.setRequestStatus(RequestStatus.OFFERED);
        requestDto.setPickLocation(LocationUtil.split(user.getLocation().getLatitude(), user.getLocation().getLongitude()));
        requestDto.setDropLocation(LocationUtil.getRandomLocation());
        try {
            final List<CarDto> allCars = carService.getCarsWithRequirement(requirement);
            DriverDto driver = findNearest(user, allCars);
            requestDto.setDriverId(driver.getUserId());
            requestDto.setRequestDate(new Date());
            log.info("founded driver  " + driver.getId());
            double distance = calculateDistance(user, driver);
            String price = calculatePrice(distance, userId);
            requestDto.setPrice(new BigDecimal(price));
            int waitingTimeInt = calculateWaitingTime(distance);
            String  waitingTime = waitingTimeInt == 0 ? "<1" : String.valueOf(waitingTimeInt);
            requestService.registerRequest(requestDto);
            request.getSession().setAttribute("driver", driver);
            request.getSession().setAttribute("request", requestDto);
            request.getSession().setAttribute("waitingTime", waitingTime);
            request.getSession().setAttribute("carRequest", requestDto);
            RequestUtil.sendRedirectToCommand(request, response, GET_SUITABLE_DRIVER_VIEW);
        } catch (DriverServiceException| RequestServiceException | DiscountServiceException | CarServiceException e) {
            log.error("Exception in find car command", e);
            throw new CommandException(e);
        }
    }

    private double calculateDistance(UserAccountDto user, DriverDto driver) {
        double latClient = user.getLocation().getLatitude();
        double longClient = user.getLocation().getLongitude();
        double latDriver = driver.getLocation().getLatitude();
        double longDriver = driver.getLocation().getLongitude();
        final double R = 6378.1;
        double sin1 = sin((latClient - latDriver) / 2);
        double sin2 = sin((longClient - longDriver) / 2);
        return 2 * R * asin(sqrt(sin1 * sin1 + sin2 * sin2 * cos(latClient) * cos(latDriver)));
    }

    private String calculatePrice(double distance, long userId) throws DiscountServiceException  {
        DiscountDto discount = discountService.getById(userId);
        double output =  3 + distance * 0.5 * (1 - discount.getAmount() * 0.01 );
        return String.format("%.2f", output).replace(',', '.');
    }

    private int calculateWaitingTime(double distance) {
        return 6  * (int) distance / 7;
    }

    private DriverDto findNearest(UserAccountDto user, List<CarDto> cars) throws DriverServiceException  {
       //TODO
        return null;
    }
}
