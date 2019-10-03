package by.training.module1.parser;


import java.util.HashMap;
import java.util.Map;

public class LineParser {

    public Map<String, String> parseLine (String  line) {
        Map<String, String> output = new HashMap<>();
        String[] content = line.split("\\|");
        for (String i : content) {
            String[] subContent = i.split(":");
            output.put(subContent[0], subContent[1]);
        }
        return output;
    }
}
