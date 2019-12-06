package by.training.taxi.user;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static by.training.taxi.ApplicationConstants.*;

@Log4j
@Bean(name=GET_ALL_USERS)
public class ViewUserListCommand implements Command {
    private UserAccountService userService;

    public ViewUserListCommand(UserAccountService userService) {

        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        final List<UserAccountDto> allUsers = userService.getAllUsers();
        req.setAttribute("users", allUsers);

        try {
            req.setAttribute(VIEWNAME_REQ_PARAMETER, VIEW_ALL_USERS_CMD_NAME);
            req.getRequestDispatcher("/jsp/layout.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            log.error("Something went wrong...", e);
            throw new CommandException(e);
        }
    }
}
