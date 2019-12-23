package by.training.taxi.user;

import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateRequestCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

    }
}
