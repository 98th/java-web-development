package by.training.module3.command;

import by.training.module3.entity.Gem;

import java.util.HashMap;
import java.util.Map;

public class ParserCommandProvider implements CommandProvider<Gem> {
    private Map<CommandType, Command<Gem>> commands;

    public ParserCommandProvider() {
        commands = new HashMap<>();
    }

    @Override
    public Command<Gem> getCommand(CommandType commandType) {
        return commands.get(commandType);
    }

    @Override
    public void addCommand(CommandType commandType, Command<Gem> command) {
        commands.put(commandType, command);
    }
}
