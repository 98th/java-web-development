package by.training.taxi.request;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.user.UserAccountDto;
import by.training.taxi.util.RequestUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static by.training.taxi.ApplicationConstants.*;
import static by.training.taxi.request.RequestStatus.CONFIRMED;
import static by.training.taxi.user.Role.DRIVER;


@Bean(name=POST_CONFIRM_REQUEST)
@Log4j
@AllArgsConstructor
public class ConfirmRequestCommand implements Command {
    private RequestService requestService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            UserAccountDto userAccountDto = (UserAccountDto) request.getSession().getAttribute(PARAM_USER);
            long id = Long.parseLong(request.getParameter(PARAM_REQUEST_ID));
            if (DRIVER == userAccountDto.getRole()){
                RequestDto requestDto = requestService.getById(id);
                requestDto.setRequestStatus(CONFIRMED);
                requestService.update(requestDto);
                RequestUtil.sendRedirectToCommand(request, response, GET_USER_PROFILE_VIEW);
            } else {
                RequestUtil.sendRedirect(request, response, PARAM_ERROR);
            }

        } catch (RequestServiceException e) {
            throw new CommandException();
        }
    }
}
