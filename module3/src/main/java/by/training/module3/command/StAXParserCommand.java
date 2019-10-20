package by.training.module3.command;

import by.training.module3.entity.Gem;
import by.training.module3.parser.Parser;
import by.training.module3.validator.XMLValidator;


public class StAXParserCommand extends ParserCommand<Gem>{
    public StAXParserCommand(XMLValidator validator, Parser parser) {
        super(validator, parser);
    }
}
