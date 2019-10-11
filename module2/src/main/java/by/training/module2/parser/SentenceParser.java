package by.training.module2.parser;

import by.training.module2.model.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceParser extends TextParser {
  private final Pattern sentencePattern = Pattern.compile("(\\s+)[^.!?]*[.!?](\n)*");
 // private final Pattern sentencePattern = Pattern.compile("(\\n)?[^.!?]*[.!?]*");

    @Override
    public TextLeaf parse(String line) {
        TextComposite parsedText = new ParagraphComposite();
        Matcher matcher = sentencePattern.matcher(line);
        while (matcher.find()) {
            parsedText.addText(nextParse(matcher.group()));
        }
        return parsedText;
    }
}
