package by.training.module3.command;

import by.training.module3.entity.Gem;
import by.training.module3.parser.GemSAXParser;
import by.training.module3.validator.XMLValidator;


public class SAXParserCommand extends ParserCommand<Gem>{

    public SAXParserCommand(XMLValidator validator) {
        super(validator, new GemSAXParser());
    }
}
