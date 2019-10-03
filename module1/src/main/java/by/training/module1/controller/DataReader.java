package by.training.module1.controller;

import by.training.module1.validator.FileValidator;
import by.training.module1.validator.ValidationResult;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataReader {
    private static final Logger log = LogManager.getLogger(DataReader.class);

    public List<String> read (String path) {
        FileValidator fileValidator = new FileValidator();
        ValidationResult validationResult = fileValidator.validateFile(path);
        List<String> lines = new ArrayList<>();
        if (validationResult.isValid()) {
            try {
                lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
                log.info("the file has been read");
            } catch (IOException e) {
                log.fatal("something went wrong while reading the file", e);
            }
        }
        return  lines;
    }

}