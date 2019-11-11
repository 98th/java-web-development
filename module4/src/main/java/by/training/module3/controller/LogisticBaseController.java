package by.training.module3.controller;

import by.training.module3.model.LogisticBase;
import by.training.module3.model.Truck;
import by.training.module3.parser.Parser;
import by.training.module3.parser.ParserException;
import by.training.module3.validator.FileValidator;
import by.training.module3.validator.ValidationResult;
import by.training.module3.validator.XMLValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogisticBaseController {
    private FileValidator fileValidator;
    private XMLValidator xmlValidator;
    private Parser<List<Truck>> truckParser;
    private Parser<LogisticBase> baseParser;
    private static final Logger log = LogManager.getLogger(LogisticBaseController.class);

    public LogisticBaseController(FileValidator fileValidator, XMLValidator xmlValidator,
                                  Parser<List<Truck>> truckParser, Parser<LogisticBase> baseParser) {
        this.fileValidator = fileValidator;
        this.xmlValidator = xmlValidator;
        this.truckParser = truckParser;
        this.baseParser = baseParser;
    }

    public List<Truck> execute(String trucksXMLPath, String baseXMLPath) {
        ValidationResult validationResult = fileValidator.validateFile(trucksXMLPath);
        validationResult.addValidationResult(fileValidator.validateFile(baseXMLPath));
        validationResult.addValidationResult(xmlValidator.validateXML(trucksXMLPath));
        if(!validationResult.isValid()) {
            log.error("File is not valid" + validationResult.getResult());
            throw new RuntimeException();
        }
        List<Truck> trucks = null;
        try{
            trucks = truckParser.parse(trucksXMLPath);
            LogisticBase logisticBase  = baseParser.parse(baseXMLPath);
            trucks.forEach(i -> i.setLogisticBase(logisticBase));
        } catch (ParserException e) {
            log.error(e);
        }
        ExecutorService service = Executors.newFixedThreadPool(trucks.size());
        trucks.forEach(service::execute);
        return trucks;
    }
}
