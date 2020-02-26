package by.training.taxi.user;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.util.RequestUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.training.taxi.ApplicationConstants.*;
import static by.training.taxi.user.Role.ADMIN;
import static by.training.taxi.user.Role.DRIVER;

@Bean(name = LOCK_USER_CMD)
@AllArgsConstructor
@Log4j
public class LockUserCommand implements Command {
    private UserAccountService userService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            Long id = Long.parseLong(request.getParameter(PARAM_USER_ID));
            UserAccountDto userDto = userService.findById(id);
            if (ADMIN == userDto.getRole()) {
                request.setAttribute(PARAM_ERROR, "error.lock.admin");
                RequestUtil.forward(request, response, GET_USER_LIST_VIEW);
                return;
            }
            if (!userDto.getIsLocked()) {
                userDto.setLocked(true);
            } else {
                userDto.setLocked(false);
            }
            userService.update(userDto);
            if (DRIVER == userDto.getRole()) {
                RequestUtil.sendRedirectToCommand(request, response, GET_DRIVER_LIST_VIEW);
                return;
            } else {
                RequestUtil.sendRedirectToCommand(request, response, GET_USER_LIST_VIEW);
            }
        } catch (UserServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
