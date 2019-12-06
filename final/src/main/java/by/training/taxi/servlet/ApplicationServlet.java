package by.training.taxi.servlet;

import by.training.taxi.ApplicationContext;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/", loadOnStartup = 1, name = "app")
public class ApplicationServlet extends HttpServlet {

    /**
     **/
    private static final long serialVersionUID = -898419077104540041L;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getParameter("commandName");
        Command command = ApplicationContext.getInstance().getBean(commandName);
        if (command != null) {
            try {
                command.execute(req, resp);
            } catch (CommandException e) {
                throw new ServletException(e);
            }
        } else {
            req.getRequestDispatcher("/jsp/layout.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}