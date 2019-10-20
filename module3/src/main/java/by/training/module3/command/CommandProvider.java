package by.training.module3.command;

public interface CommandProvider<T> {
    Command<T> getCommand (CommandType commandType);
    void addCommand (CommandType commandType, Command<T> command);
}
