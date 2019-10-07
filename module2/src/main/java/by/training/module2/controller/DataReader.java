package by.training.module2.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataReader {
    private static final Logger log = LogManager.getLogger(DataReader.class);

    public String read (String path) {
        List<String> lines = new ArrayList<>();
        try{
            lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
            log.info("the file has been read");
        } catch (IOException e) {
            log.fatal("something went wrong while reading the file", e);
        }
        return  lines.stream().collect(Collectors.joining("\n"));
    }
}
