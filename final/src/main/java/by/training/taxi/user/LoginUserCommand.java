package by.training.taxi.user;


import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.util.Md5Util;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static by.training.taxi.ApplicationConstants.*;

@Bean(name=POST_USER_LOGIN)
@AllArgsConstructor
@Log4j
public class LoginUserCommand implements Command {
    private UserAccountService userAccountService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        String login = request.getParameter(PARAM_USER_LOGIN);
        String password = Md5Util.md5Apache(request.getParameter(PARAM_USER_PASSWORD));
        try {
            Optional<UserAccountDto> userAccountDtoOptional = userAccountService.findByLoginAndPassword(login, password);
            if(userAccountDtoOptional.isPresent()) {
                UserAccountDto userAccountDto = userAccountDtoOptional.get();
                HttpSession session = request.getSession(true);
                session.setAttribute(PARAM_USER, userAccountDto);
                session.setAttribute(PARAM_USER_ROLE, userAccountDto.getRole().toString());
                response.sendRedirect(request.getContextPath() + "?commandName=" + USER_PAGE_CMD);
            } else {
                request.setAttribute(VIEWNAME_REQ_PARAMETER, GET_LOGIN_VIEW);
                request.setAttribute("error", "Wrong login or password");
                request.getRequestDispatcher("jsp/layout.jsp").forward(request, response);
            }
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }
}
