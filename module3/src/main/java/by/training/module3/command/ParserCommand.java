package by.training.module3.command;

import by.training.module3.parser.Parser;
import by.training.module3.parser.ParserException;
import by.training.module3.validator.XMLValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class ParserCommand<T> implements Command<T> {
    private XMLValidator validator;
    private Parser parser;
    private static final Logger log = LogManager.getLogger(ParserCommand.class);

    public ParserCommand(XMLValidator validator, Parser parser) {
        this.validator = validator;
        this.parser = parser;
    }

    @Override
    public List<T> build(String path) {
         if (!validator.validateXML(path)) {
             log.error("exception while reading a XML file");
             throw new RuntimeException();
         }
         try {
             return parser.parse(path);
         } catch (ParserException e) {
             throw new RuntimeException();
         }
    }
}
