package by.training.taxi.request;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.driver.DriverDto;
import by.training.taxi.driver.DriverService;
import by.training.taxi.driver.DriverServiceException;
import by.training.taxi.user.UserAccountDto;
import by.training.taxi.user.UserAccountService;
import by.training.taxi.user.UserServiceException;
import by.training.taxi.util.RequestUtil;
import by.training.taxi.wallet.WalletDto;
import by.training.taxi.wallet.WalletService;
import by.training.taxi.wallet.WalletServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;

import static by.training.taxi.ApplicationConstants.*;
import static by.training.taxi.request.RequestStatus.*;

@Log4j
@AllArgsConstructor
@Bean(name=POST_CHOOSE_CAR)
public class ChooseCarCommand implements Command {
    private UserAccountService userAccountService;
    private RequestService requestService;
    private WalletService walletService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            UserAccountDto user = (UserAccountDto) request.getSession().getAttribute(PARAM_USER);
            long userId = user.getId();
            String curW = request.getParameter("currentWallet");
            log.info("wallet to be used" + curW);
            long walletId = Long.parseLong(curW);
            long requestId = (Long)request.getSession().getAttribute(PARAM_REQUEST_ID);
            RequestDto requestDto = requestService.getById(requestId);
            WalletDto currentWallet = walletService.getByUserId(userId)
                    .stream().filter(i -> walletId == i.getId()).findFirst().orElse(new WalletDto());
            long driverId = requestDto.getDriverId();
            BigDecimal requestPrice = requestDto.getPrice();
            BigDecimal walletAmount = currentWallet.getAmount();
            if (ableToPay(requestPrice, walletAmount)) {
                requestDto.setDriverId(driverId);
                requestDto.setRequestStatus(ACCEPTED);
                log.info("request accepted " + requestId);
                requestService.update(requestDto);
                userAccountService.transfer(userId, driverId, requestPrice);
                RequestUtil.sendRedirectToCommand(request, response, GET_REQUEST_INFO_VIEW);
            } else {
                request.setAttribute(PARAM_ERROR, "error.not.enough.money");
                requestDto.setRequestStatus(DECLINED);
                requestService.registerRequest(requestDto);
                RequestUtil.forward(request, response, GET_REQUEST_INFO_VIEW);
                return;
            }
        } catch (RequestServiceException | UserServiceException  | WalletServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }
    private boolean ableToPay(BigDecimal price, BigDecimal walletAmount) {
        return walletAmount.compareTo(price) >= 0;
    }
}
