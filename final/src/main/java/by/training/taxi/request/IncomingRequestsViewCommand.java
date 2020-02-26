package by.training.taxi.request;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.user.UserAccountDto;
import by.training.taxi.util.RequestUtil;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static by.training.taxi.ApplicationConstants.*;
import static by.training.taxi.request.RequestStatus.ACCEPTED;
import static by.training.taxi.request.RequestStatus.OFFERED;
import static by.training.taxi.user.Role.DRIVER;

@Bean(name=GET_INCOMING_REQUESTS_VIEW)
@AllArgsConstructor
public class IncomingRequestsViewCommand implements Command {
    private RequestService requestService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            UserAccountDto userAccountDto = (UserAccountDto) request.getSession().getAttribute(PARAM_USER);
            if (DRIVER == userAccountDto.getRole()) {
                List<RequestDto> requestList = requestService.getAllByDriverId(userAccountDto.getId()).stream()
                        .filter(i -> ACCEPTED == i.getRequestStatus()).collect(Collectors.toList());
                request.setAttribute("incomingRequests", requestList);
                request.setAttribute(VIEWNAME_REQ_PARAMETER, GET_INCOMING_REQUESTS_VIEW);
                request.getRequestDispatcher("/jsp/layout.jsp").forward(request, response);
            } else {
                RequestUtil.sendRedirect(request, response, PARAM_ERROR);
            }
        } catch (RequestServiceException| ServletException | IOException e) {
            throw new CommandException();
        }
    }
}
