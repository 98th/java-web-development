package by.training.taxi.user;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.util.RequestUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.training.taxi.ApplicationConstants.GET_USER_LIST_VIEW;
import static by.training.taxi.ApplicationConstants.LOCK_USER_CMD;
import static by.training.taxi.user.Role.ADMIN;

@Bean(name = LOCK_USER_CMD)
@AllArgsConstructor
@Log4j
public class LockUserCommand implements Command {
    private UserAccountService userService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            Long id = Long.parseLong(request.getParameter("userId"));
            UserAccountDto userDto = userService.findById(id);
            if (ADMIN == userDto.getRole()) {
                request.setAttribute("error", "error.lock.admin");
                RequestUtil.forward(request, response, GET_USER_LIST_VIEW);
            }
            if (!userDto.getIsLocked()) {
                userDto.setLocked(true);
            } else {
                userDto.setLocked(false);
            }
            userService.update(userDto);
            RequestUtil.sendRedirectToCommand(request, response, GET_USER_LIST_VIEW);
        } catch (UserServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
