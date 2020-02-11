package by.training.taxi.wallet;


import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.util.RequestUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.training.taxi.ApplicationConstants.POST_DELETE_WALLET;
import static by.training.taxi.ApplicationConstants.USER_PROFILE_CMD;

@Bean(name = POST_DELETE_WALLET)
@AllArgsConstructor
@Log4j
public class DeleteWalletCommand implements Command {
    private WalletService walletService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Long id = Long.parseLong(request.getParameter("walletId"));
        walletService.delete(id);
        log.info("wallet " + id + " has been deleted");
        RequestUtil.sendRedirectToCommand(request, response, USER_PROFILE_CMD);
    }
}
