package by.training.taxi.wallet;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.user.UserAccountDto;
import by.training.taxi.util.RequestUtil;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;

import static by.training.taxi.ApplicationConstants.*;

@AllArgsConstructor
@Bean(name = POST_ADD_WALLET)
public class AddWalletCommand implements Command {
    private WalletService walletService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            UserAccountDto userAccountDto = (UserAccountDto) request.getSession().getAttribute(PARAM_USER);
            WalletDto walletDto = WalletDto.builder()
                    .userId(userAccountDto.getId())
                    .amount(new BigDecimal("0"))
                    .build();
            walletService.save(walletDto);
            userAccountDto.getWallets().add(walletDto);
            RequestUtil.sendRedirectToCommand(request, response, GET_WALLET_VIEW);
        } catch (WalletServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
