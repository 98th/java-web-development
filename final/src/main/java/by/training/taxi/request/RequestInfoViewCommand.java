package by.training.taxi.request;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.discount.DiscountDto;
import by.training.taxi.discount.DiscountService;
import by.training.taxi.discount.DiscountServiceException;
import by.training.taxi.driver.DriverDto;
import by.training.taxi.driver.DriverService;
import by.training.taxi.driver.DriverServiceException;
import by.training.taxi.location.LocationDto;
import by.training.taxi.location.LocationService;
import by.training.taxi.location.LocationServiceException;
import by.training.taxi.user.UserAccountDto;
import by.training.taxi.wallet.WalletDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static by.training.taxi.ApplicationConstants.*;
import static java.lang.Math.*;
import static java.lang.Math.cos;

@Log4j
@Bean(name=GET_REQUEST_INFO_VIEW)
@AllArgsConstructor
public class RequestInfoViewCommand implements Command {
    private RequestService requestService;
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            UserAccountDto userAccountDto = (UserAccountDto) request.getSession().getAttribute(PARAM_USER);
            List<WalletDto> wallets = userAccountDto.getWallets();
            request.setAttribute("wallets", wallets);
            long requestId = (Long)request.getSession().getAttribute(PARAM_REQUEST_ID);
            RequestDto requestDto = requestService.getById(requestId);
            request.setAttribute(PARAM_REQUEST, requestDto);
            request.setAttribute(VIEWNAME_REQ_PARAMETER, GET_REQUEST_INFO_VIEW);
            request.getRequestDispatcher("/jsp/layout.jsp").forward(request, response);
        } catch (ServletException | IOException | RequestServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }

}