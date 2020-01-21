package by.training.taxi.user;


import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.util.Md5Util;
import by.training.taxi.util.RequestUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static by.training.taxi.ApplicationConstants.*;

@Bean(name=POST_USER_LOGIN)
@AllArgsConstructor
@Log4j
public class LoginUserCommand implements Command {
    private UserAccountService userAccountService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String login = request.getParameter(PARAM_USER_LOGIN);
        String password = Md5Util.md5Apache(request.getParameter(PARAM_USER_PASSWORD));
        try {
            Optional<UserAccountDto> userAccountDtoOptional = userAccountService.findByLoginAndPassword(login, password);
            if(userAccountDtoOptional.isPresent()) {
                if(userAccountDtoOptional.get().getIsLocked()) {
                    request.setAttribute(PARAM_ERROR, "error.blocking");
                    RequestUtil.forward(request, response, GET_LOGIN_VIEW);
                    return;
                }
                UserAccountDto userAccountDto = userAccountDtoOptional.get();
                request.getSession().setAttribute(PARAM_USER, userAccountDto);
                request.getSession().setAttribute(PARAM_USER_ROLE, userAccountDto.getRole());
                RequestUtil.sendRedirectToCommand(request, response, USER_PAGE_CMD);
            } else {
                request.setAttribute(PARAM_ERROR, "error.wrong.login.or.pass");
                RequestUtil.forward(request, response, GET_LOGIN_VIEW);
            }
        } catch (UserServiceException  e) {
            throw new CommandException(e.getMessage());
        }
    }
}
