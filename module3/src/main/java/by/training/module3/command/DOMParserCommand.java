package by.training.module3.command;

import by.training.module3.entity.Gem;
import by.training.module3.parser.GemDOMParser;
import by.training.module3.parser.Parser;
import by.training.module3.parser.ParserException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class DOMParserCommand implements Command<Gem>{
    private Parser<Gem> parser = new GemDOMParser();
    private static final Logger log = LogManager.getLogger(SAXParserCommand.class);


    @Override
    public List<Gem> parse (String path) throws CommandException {
        try {
            return parser.parse(path);
        } catch (ParserException e) {
            log.error("exception in DOM parser");
            throw new CommandException(e);
        }
    }
}
