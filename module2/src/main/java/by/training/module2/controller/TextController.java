package by.training.module2.controller;

import by.training.module2.model.*;
import by.training.module2.parser.ParserChain;
import by.training.module2.service.ParagraphCompositeService;
import by.training.module2.service.TextService;
import by.training.module2.service.WordLeafService;
import by.training.module2.validator.FileValidator;
import by.training.module2.validator.ValidationResult;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class TextController {
    private static final Logger log = LogManager.getLogger(TextController.class);
    private DataReader reader;
    private TextService service;
    private FileValidator fileValidator;
    private ParserChain<TextLeaf> parser;

    public TextController(DataReader reader, TextService service, FileValidator fileValidator, ParserChain<TextLeaf> parser) {
        this.reader = reader;
        this.service = service;
        this.fileValidator = fileValidator;
        this.parser = parser;
    }

    public List<TextLeaf> parseText(String path) {
        ValidationResult validationResult = fileValidator.validateFile(path);
        String text = reader.read(path);
        if (!validationResult.isValid()) {
            log.error("invalid file " + validationResult.getResult());
        }
        List<TextLeaf> leaves = parser.parse(text);
        return leaves;
    }

    public WholeTextComposite composeText (List<TextLeaf> leaves) {
        WholeTextComposite textComposite = new WholeTextComposite(leaves);
        return textComposite;
    }
}
