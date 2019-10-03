package by.training.module1.validator;

import by.training.module1.controller.DataReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;

public class FileValidator {
    private static final Logger log = LogManager.getLogger(DataReader.class);

    public ValidationResult validateFile(String path) {
        ValidationResult validationResult = new ValidationResult();

        if (path == null) {
            validationResult.addMessage("incorrect data", "path is invalid");
            log.fatal("File path is invalid");
            return validationResult;
        }

        File file = new File(path);

        if (!file.exists()) {
            validationResult.addMessage("existence", "There is no such file");
            log.fatal("Absence of file");
            return validationResult;
        }
        if (!file.isFile()) {
            validationResult.addMessage("existence", "This is not a file");
            log.fatal("Absence of file");
            return validationResult;
        }
        if (file.length() == 0) {
            validationResult.addMessage("length", "The file is empty");
            log.fatal("Empty file");
            return validationResult;
        }
        return validationResult;
    }
}
