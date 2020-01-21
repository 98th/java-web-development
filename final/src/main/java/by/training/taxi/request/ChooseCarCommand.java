package by.training.taxi.request;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.training.taxi.ApplicationConstants.POST_CHOOSE_CAR;

@Bean(name=POST_CHOOSE_CAR)
public class ChooseCarCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

    }
}
