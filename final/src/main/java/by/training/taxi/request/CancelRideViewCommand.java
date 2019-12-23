package by.training.taxi.request;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.training.taxi.ApplicationConstants.GET_CANCEL_RIDE_VIEW;
import static by.training.taxi.ApplicationConstants.VIEWNAME_REQ_PARAMETER;

@Bean(name = GET_CANCEL_RIDE_VIEW)
public class CancelRideViewCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            request.setAttribute(VIEWNAME_REQ_PARAMETER, GET_CANCEL_RIDE_VIEW);
            request.getRequestDispatcher("/jsp/layout.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
