package by.training.taxi.user;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.taxi.ApplicationConstants.GET_USER_REGISTRATION;
import static by.training.taxi.ApplicationConstants.VIEWNAME_REQ_PARAMETER;

@Bean(name=GET_USER_REGISTRATION)
public class RegistrationUserViewCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)  {
        try{
            request.setAttribute(VIEWNAME_REQ_PARAMETER, GET_USER_REGISTRATION);
            request.getRequestDispatcher("/jsp/layout.jsp").forward(request, response);
        } catch (ServletException | IOException e) {}
    }
}
