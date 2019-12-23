package by.training.taxi.user;

import by.training.taxi.SecurityContext;
import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.training.taxi.ApplicationConstants.LOGOUT_CMD_NAME;

@Bean(name = LOGOUT_CMD_NAME)
@Log4j
public class LogoutUserCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            SecurityContext.getInstance().deleteSession(request.getSession());
            response.sendRedirect(request.getContextPath());
        } catch (IOException e) {
            log.error("cannot log out the user");
            throw new CommandException();
        }
    }
}