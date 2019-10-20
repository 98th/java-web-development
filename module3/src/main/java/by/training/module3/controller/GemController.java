package by.training.module3.controller;

import by.training.module3.command.*;
import by.training.module3.entity.Gem;
import by.training.module3.service.Service;
import by.training.module3.validator.FileValidator;
import by.training.module3.validator.ValidationResult;
import by.training.module3.validator.XMLValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;


public class GemController {
    private Service<Gem> service;
    private FileValidator fileValidator;
    private CommandProvider<Gem> commandProvider;
    private XMLValidator xmlValidator;
    private static final Logger log = LogManager.getLogger(GemController.class);

    public GemController(Service<Gem> service, XMLValidator xmlValidator, FileValidator fileValidator, CommandProvider<Gem> commandProvider) {
        this.service = service;
        this.xmlValidator = xmlValidator;
        this.fileValidator = fileValidator;
        this.commandProvider = commandProvider;
    }

    public void execute (String path, CommandType commandType){
        ValidationResult validationResult = fileValidator.validateFile(path);
        if(!validationResult.isValid()) {
            log.error("File is not valid" + validationResult.getResult());
            throw new RuntimeException();
        }
        ValidationResult validationResult1 = xmlValidator.validateXML(path);
        if(!validationResult1.isValid()) {
            log.error("File is not valid" + validationResult.getResult());
            throw new RuntimeException();
        }
        List<Gem> gems;
        Command<Gem> command = commandProvider.getCommand(commandType);
        try {
            gems = command.parse(path);
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }
        gems.forEach(service::add);
    }
}
