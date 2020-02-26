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
public class CarListWithRequirementCommand implements Command {
    private CarService carService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String requirement = request.getParameter(PARAM_REQUIREMENT);
        try {
            List<CarDto> cars = carService.getCarsWithRequirement(requirement);
            request.setAttribute(PARAM_FREE_CARS, cars);
            RequestUtil.sendRedirectToCommand(request, response, GET_CAR_LIST_VIEW);
        } catch (CarServiceException e) {
            log.error("Exception in find car command", e);
            throw new CommandException(e);
        }
    }


}
