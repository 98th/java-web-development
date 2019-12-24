package by.training.taxi.driver;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.util.RequestUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static by.training.taxi.ApplicationConstants.GET_DRIVER_LIST_VIEW;
import static by.training.taxi.ApplicationConstants.VIEWNAME_REQ_PARAMETER;

@Log4j
@Bean(name = GET_DRIVER_LIST_VIEW)
@AllArgsConstructor
public class DriverListViewCommand implements Command {
    private DriverService driverService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            final List<DriverDto> allDrivers = driverService.findAllWithInfo();
            req.setAttribute("drivers", allDrivers);
            RequestUtil.forward(req, resp, GET_DRIVER_LIST_VIEW);
        } catch (DriverServiceException e) {
            log.error("Exception occurred while displaying the driver list ", e);
            throw new CommandException(e);
        }
    }
}