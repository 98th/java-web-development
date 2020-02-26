package by.training.taxi.user;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.util.ImageUtil;
import by.training.taxi.util.RequestUtil;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.IOException;

import static by.training.taxi.ApplicationConstants.*;

@Bean(name = UPDATE_AVATAR_CMD)
@AllArgsConstructor
public class UpdateUserAvatarCommand implements Command {
    private UserAccountService userAccountService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            Part img = request.getPart(PARAM_USER_AVATAR);
            byte[] imageByte = ImageUtil.toBytes(img);
            UserAccountDto user = (UserAccountDto) request.getSession().getAttribute(PARAM_USER);
            user.setAvatar(imageByte);
            userAccountService.update(user);
            RequestUtil.sendRedirectToCommand(request, response, USER_PROFILE_CMD);
        } catch (IOException | ServletException | UserServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
