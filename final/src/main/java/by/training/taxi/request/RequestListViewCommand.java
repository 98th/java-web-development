package by.training.taxi.request;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.user.UserAccountDto;
import by.training.taxi.user.UserServiceException;
import by.training.taxi.util.RequestUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.h2.engine.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static by.training.taxi.ApplicationConstants.*;

@Bean(name=GET_REQUEST_LIST_VIEW)
@Log4j
@AllArgsConstructor
public class RequestListViewCommand implements Command {
    private RequestService requestService;
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            UserAccountDto user = (UserAccountDto)req.getSession().getAttribute(PARAM_USER);
            final List<RequestDto> requests = requestService.getAllByClientId(user.getId());
            req.setAttribute("requests", requests);
            RequestUtil.forward(req, resp, GET_REQUEST_LIST_VIEW);
        } catch (RequestServiceException e) {
            log.error("Exception occurred while displaying the request list ", e);
            throw new CommandException(e);
        }
    }
}
