package by.training.taxi.request;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.util.RequestUtil;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.training.taxi.ApplicationConstants.DELETE_REQUEST_CMD;
import static by.training.taxi.ApplicationConstants.GET_REQUEST_LIST_VIEW;

@Bean(name=DELETE_REQUEST_CMD)
@Log4j
public class DeleteRequestCommand implements Command {
    private RequestService requestService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            Long id = Long.parseLong(request.getParameter("requestId"));
            RequestDto requestDto = requestService.getById(id);
         //   requestDto.setClientId();
            requestService.update(requestDto);
            log.info("discount "  + " was assigned to the user " + id);
            RequestUtil.sendRedirectToCommand(request, response, GET_REQUEST_LIST_VIEW);
        } catch (RequestServiceException e) {
            log.error("Failed to assign a discount to the user");
            throw new CommandException();
        }
    }
}
