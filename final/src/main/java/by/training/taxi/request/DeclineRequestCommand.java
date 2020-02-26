package by.training.taxi.request;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.user.UserAccountDto;
import by.training.taxi.util.RequestUtil;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.training.taxi.ApplicationConstants.*;
import static by.training.taxi.request.RequestStatus.CONFIRMED;
import static by.training.taxi.request.RequestStatus.DECLINED;
import static by.training.taxi.user.Role.DRIVER;

@Bean(name=POST_DECLINE_REQUEST)
@AllArgsConstructor
public class DeclineRequestCommand implements Command {
    private RequestService requestService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            UserAccountDto userAccountDto = (UserAccountDto) request.getSession().getAttribute(PARAM_USER);
            long id = Long.parseLong(request.getParameter("requestId"));
            if (DRIVER == userAccountDto.getRole()){
                RequestDto requestDto = requestService.getById(id);
                requestDto.setRequestStatus(DECLINED);
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
