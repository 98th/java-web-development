package by.training.module3.command;

import by.training.module3.entity.Gem;
import by.training.module3.parser.GemSAXParser;
import by.training.module3.parser.Parser;
import by.training.module3.parser.ParserException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;


public class SAXParserCommand implements Command<Gem>{
    private Parser<Gem> parser = new GemSAXParser();
    private static final Logger log = LogManager.getLogger(SAXParserCommand.class);


    @Override
    public List<Gem> parse (String path) throws CommandException {
        try {
            return parser.parse(path);
        } catch (ParserException e) {
            log.error("exception in SAX parser");
            throw new CommandException();
        }
    }
}
