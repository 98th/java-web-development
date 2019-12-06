package by.training.taxi.user;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static by.training.taxi.ApplicationConstants.LOGOUT_CMD_NAME;

@Bean(name=LOGOUT_CMD_NAME)
public class LogoutUserCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
