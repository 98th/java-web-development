package by.training.module3.command;

import by.training.module3.entity.Gem;

import java.util.List;


public class StAXParserCommand implements Command<Gem>{
    @Override
    public List<Gem> parse(String path) throws CommandException {
        return null;
    }
}
