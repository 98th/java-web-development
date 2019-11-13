package by.training.module1.controller;

import by.training.module1.builder.AircraftBuilder;
import by.training.module1.builder.AircraftBuilderFactory;
import by.training.module1.entity.Aircraft;
import by.training.module1.entity.AircraftType;
import by.training.module1.parser.LineParser;
import by.training.module1.service.AircraftService;
import by.training.module1.validator.FileValidator;
import by.training.module1.validator.LineValidator;
import by.training.module1.validator.ValidationResult;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class AircraftController {
    private static final Logger log = LogManager.getLogger(AircraftController.class);
    private DataReader reader;
    private AircraftService service;
    private FileValidator fileValidator;
    private LineParser lineParser;
    private AircraftBuilderFactory factory;
    private LineValidator lineValidator;

    public AircraftController(DataReader reader, AircraftService service, FileValidator fileValidator, LineParser lineParser,
                              AircraftBuilderFactory factory, LineValidator lineValidator) {
        this.reader = reader;
        this.service = service;
        this.fileValidator = fileValidator;
        this.lineParser = lineParser;
        this.factory = factory;
        this.lineValidator = lineValidator;
    }

    public void execute(String path) {
        ValidationResult validationResult = fileValidator.validateFile(path);
        if (validationResult.isValid()) {
            int counter = 0;
            List<String> lines = reader.read(path);
            Map<String, String> parsedLine;
            for (String i : lines) {
                counter++;
                validationResult = lineValidator.validateLine(i);
                if (validationResult.isValid()) {
                    parsedLine = lineParser.parseLine(i);
                    AircraftBuilder aircraftBuilder = factory.getAircraft(AircraftType.valueOf(parsedLine.get("type").toUpperCase()));
                    Aircraft aircraft = aircraftBuilder.build(parsedLine);
                    service.add(aircraft);
                } else {
                    log.warn(counter + "th line is invalid" + validationResult.getResult());
                }
            }
        } else {
            log.error("invalid file " + validationResult.getResult());
        }
    }
}
