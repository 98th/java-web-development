package by.training.taxi.car;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.request.RequestDto;
import by.training.taxi.request.RequestStatus;
import by.training.taxi.user.UserAccountDto;
import by.training.taxi.util.LocationUtil;
import lombok.AllArgsConstructor;
import org.h2.engine.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static by.training.taxi.ApplicationConstants.*;

@Bean(name = GET_CAR_LIST_VIEW)
@AllArgsConstructor
public class CarListView implements Command {
    private CarService carService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            List<CarDto> cars = carService.getAllFree();
            request.setAttribute(PARAM_FREE_CARS, cars);
            request.setAttribute(VIEWNAME_REQ_PARAMETER, GET_CAR_LIST_VIEW);
            request.getRequestDispatcher("/jsp/layout.jsp").forward(request, response);
        } catch (ServletException | CarServiceException | IOException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
