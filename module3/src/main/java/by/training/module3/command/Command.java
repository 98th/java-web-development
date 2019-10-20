package by.training.module3.command;

import java.util.List;

public interface Command<T> {
    List<T> parse (String path) throws CommandException;
}
