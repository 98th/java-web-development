package by.training.taxi.request;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.util.RequestUtil;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.training.taxi.ApplicationConstants.*;
import static by.training.taxi.request.RequestStatus.DECLINED;

@Bean(name=DECLINE_CMD)
@AllArgsConstructor
public class DeclineRideCommand implements Command {
    private RequestService requestService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            RequestDto requestDto = (RequestDto) request.getSession().getAttribute(PARAM_REQUEST);
            requestDto.setRequestStatus(DECLINED);
            requestService.update(requestDto);
            if (requestService.update(requestDto)) {
                RequestUtil.sendRedirectToCommand(request, response, USER_PROFILE_CMD);
            }
        } catch (RequestServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
