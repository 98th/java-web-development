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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

import static by.training.taxi.ApplicationConstants.*;

@Bean(name=POST_CONFIRM_REQUEST)
@Log4j
@AllArgsConstructor
public class ConfirmRequestCommand implements Command {
    private UserAccountService userAccountService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            RequestDto requestDto = (RequestDto) request.getSession().getAttribute("carRequest");
            UserAccountDto user = (UserAccountDto) request.getSession().getAttribute(PARAM_USER);
            long driverId = requestDto.getDriverId();
            long userId = requestDto.getClientId();
            BigDecimal requestPrice = requestDto.getPrice();
            BigDecimal walletAmount = user.getWallet().getAmount();
            if (ableToPay(requestPrice, walletAmount)) {
                userAccountService.transfer(driverId, userId, requestPrice);
                RequestUtil.sendRedirectToCommand(request, response, GET_CANCEL_RIDE_VIEW);
            }
        } catch (UserServiceException e) {
            throw  new CommandException();
        }
    }

    private boolean ableToPay(BigDecimal price, BigDecimal walletAmount) {
        return walletAmount.compareTo(price) >= 0;
    }
}
