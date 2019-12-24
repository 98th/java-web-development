package by.training.taxi.request;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.user.UserAccountDto;
import by.training.taxi.user.UserAccountService;
import by.training.taxi.user.UserServiceException;
import by.training.taxi.util.RequestUtil;
import by.training.taxi.wallet.WalletDto;
import by.training.taxi.wallet.WalletServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

import static by.training.taxi.ApplicationConstants.*;
import static by.training.taxi.request.RequestStatus.CONFIRM;
import static by.training.taxi.request.RequestStatus.DECLINED;


@Bean(name=POST_CONFIRM_REQUEST)
@Log4j
@AllArgsConstructor
public class ConfirmRequestCommand implements Command {
    private UserAccountService userAccountService;
    private RequestService requestService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            RequestDto requestDto = (RequestDto) request.getSession().getAttribute(PARAM_REQUEST);
            UserAccountDto user = (UserAccountDto) request.getSession().getAttribute(PARAM_USER);
            long driverId = requestDto.getDriverId();
            long userId = requestDto.getClientId();
            BigDecimal requestPrice = requestDto.getPrice();
            BigDecimal walletAmount = user.getWallet().getAmount();
            if (ableToPay(requestPrice, walletAmount)) {
                requestDto.setRequestStatus(CONFIRM);
                requestService.update(requestDto);
                userAccountService.transfer(driverId, userId, requestPrice);
                request.setAttribute(VIEWNAME_REQ_PARAMETER, GET_CANCEL_RIDE_VIEW);
                request.getRequestDispatcher("/jsp/layout.jsp").forward(request, response);
            } else {
                request.setAttribute(PARAM_ERROR, "error.not.enough.money");
                requestDto.setRequestStatus(DECLINED);
                requestService.update(requestDto);
                RequestUtil.forward(request, response, GET_SUITABLE_DRIVER_VIEW);
                return;
            }
        } catch (UserServiceException | RequestServiceException | IOException | ServletException e) {
            throw  new CommandException();
        }
    }

    private boolean ableToPay(BigDecimal price, BigDecimal walletAmount) {
        return walletAmount.compareTo(price) >= 0;
    }
}
