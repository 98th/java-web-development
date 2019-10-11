package by.training.module2.controller;

import by.training.module2.model.*;
import by.training.module2.parser.ParserChain;
import by.training.module2.service.TextService;
import by.training.module2.validator.FileValidator;
import by.training.module2.validator.ValidationResult;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class TextController {
    private static final Logger log = LogManager.getLogger(TextController.class);
    private DataReader reader;
    private TextService service;
    private FileValidator fileValidator;
    private ParserChain<TextLeaf> parser;

    public TextController(DataReader reader, TextService service, FileValidator fileValidator,
                          ParserChain<TextLeaf> parser) {
        this.reader = reader;
        this.service = service;
        this.fileValidator = fileValidator;
        this.parser = parser;
    }

    public void save(String path) {
        ValidationResult validationResult = fileValidator.validateFile(path);
        if (!validationResult.isValid()) {
            log.error("invalid file " + validationResult.getResult());
        }
        String text = reader.read(path);
        TextLeaf textLeaf = parser.parse(text);
        service.add(textLeaf);
    }
}
