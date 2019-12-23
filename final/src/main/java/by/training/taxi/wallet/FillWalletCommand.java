package by.training.taxi.wallet;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.user.UserAccountDto;
import by.training.taxi.user.UserAccountService;
import by.training.taxi.util.RequestUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;

import static by.training.taxi.ApplicationConstants.*;

@Bean(name=POST_FILL_WALLET)
@AllArgsConstructor
@Log4j
public class FillWalletCommand implements Command {
    private UserAccountService userAccountService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            BigDecimal amount = new BigDecimal(request.getParameter("amount"));
            UserAccountDto user = (UserAccountDto)request.getSession().getAttribute(PARAM_USER);
            userAccountService.fill(user.getId(), amount);
            log.info("wallet was updated with value " + amount);
            RequestUtil.sendRedirectToCommand(request, response, USER_PAGE_CMD);
        } catch (WalletServiceException e) {
            throw new CommandException("Failed to fill the wallet ");
        }
    }
}
