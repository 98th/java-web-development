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
    private final Pattern sentencePattern = Pattern.compile("\\s+[^.!?]*[.!?](\n)?");

    @Override
    public List<TextComposite> parse(String line) {
        List<TextComposite> output = new LinkedList<>();
        List<String> lines = new ArrayList<>();
        Matcher matcher = sentencePattern.matcher(line);
        while (matcher.find()) {
            lines.add(matcher.group());
        }
        for (String i : lines) {
            TextComposite sentence = new SentenceComposite();
            for (TextLeaf j : nextParse(i)) {
                sentence.addText(j);
            }
            output.add(sentence);
        }
        return output;
    }
}
