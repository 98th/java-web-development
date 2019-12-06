package by.training.taxi.command;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.taxi.ApplicationConstants.ABOUT_VIEW;
import static by.training.taxi.ApplicationConstants.VIEWNAME_REQ_PARAMETER;

@Bean(name=ABOUT_VIEW)
public class AboutViewCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ServletException, IOException {
        try {
            request.setAttribute(VIEWNAME_REQ_PARAMETER, ABOUT_VIEW);
            request.getRequestDispatcher("/jsp/layout.jsp").forward(request, response);
        } catch (IOException e) {}
    }
}
