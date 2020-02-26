package by.training.taxi.servlet;

import by.training.taxi.ApplicationContext;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
@WebServlet(urlPatterns = "/", loadOnStartup = 1, name = "app")
public class ApplicationServlet extends HttpServlet {

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