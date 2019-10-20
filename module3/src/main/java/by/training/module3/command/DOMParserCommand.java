package by.training.module3.command;

import by.training.module3.entity.Gem;
import by.training.module3.parser.GemDOMParser;
import by.training.module3.validator.XMLValidator;

public class DOMParserCommand extends ParserCommand<Gem>{

    public DOMParserCommand(XMLValidator validator) {
        super(validator, new GemDOMParser());
    }
}
