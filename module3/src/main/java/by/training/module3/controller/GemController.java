package by.training.module3.controller;

import by.training.module3.command.Command;
import by.training.module3.command.CommandProvider;
import by.training.module3.command.CommandType;
import by.training.module3.command.ParserCommandProvider;
import by.training.module3.entity.Gem;
import by.training.module3.service.GemService;
import by.training.module3.service.Service;
import by.training.module3.validator.FileValidator;
import by.training.module3.validator.ValidationResult;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;


public class GemController {
    private Service<Gem> service;
    private FileValidator fileValidator;
    private CommandProvider<Gem> commandProvider;
    private static final Logger log = LogManager.getLogger(GemController.class);

    public GemController(Service<Gem> service, FileValidator fileValidator, CommandProvider<Gem> commandProvider) {
        this.service = service;
        this.fileValidator = fileValidator;
        this.commandProvider = commandProvider;
    }

    public void execute (String path, CommandType commandType){
        ValidationResult validationResult = fileValidator.validateFile(path);
        if(!validationResult.isValid()) {
            log.error("File is not valid" + validationResult.getResult());
            throw new RuntimeException();
        }
        Command<Gem> command = commandProvider.getCommand(commandType);
        List<Gem> gems = command.build(path);
        gems.forEach(service::add);
    }
}
