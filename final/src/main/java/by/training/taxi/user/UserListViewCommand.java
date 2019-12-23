package by.training.taxi.user;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static by.training.taxi.ApplicationConstants.GET_USER_LIST_VIEW;
import static by.training.taxi.ApplicationConstants.VIEWNAME_REQ_PARAMETER;

@Log4j
@Bean(name = GET_USER_LIST_VIEW)
@AllArgsConstructor
public class UserListViewCommand implements Command {
    private UserAccountService userService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            final List<UserAccountDto> allUsers = userService.getAllWithContact();
            req.setAttribute("users", allUsers);
            req.setAttribute(VIEWNAME_REQ_PARAMETER, GET_USER_LIST_VIEW);
            req.getRequestDispatcher("/jsp/layout.jsp").forward(req, resp);
        } catch (UserServiceException | ServletException | IOException e) {
            log.error("Exception occurred while displaying the user list ", e);
            throw new CommandException(e);
        }
    }
}
