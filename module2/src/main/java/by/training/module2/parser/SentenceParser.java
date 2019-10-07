package by.training.module2.parser;

import by.training.module2.model.SentenceComposite;
import by.training.module2.model.TextComposite;
import by.training.module2.model.TextLeaf;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceParser extends TextParser {
    private final Pattern sentencePattern = Pattern.compile("\\s+[^.!?]*[.!?](\n)*");

    @Override
    public List<TextLeaf> parse(String line) {
        List<TextLeaf> output = new LinkedList<>();
        List<String> lines = new ArrayList<>();
        Matcher matcher = sentencePattern.matcher(line);
        while (matcher.find()) {
            lines.add(matcher.group());
        }
   //     long id = 1;
        for (String i : lines) {
            boolean isNewLine = i.matches("(\\s{4})+(.+)");
            TextComposite sentence = new SentenceComposite(isNewLine);
            for (TextLeaf j : nextParse(i)) {
                sentence.addText(j);
            }
            output.add(sentence);
        }
        return output;
    }
}
