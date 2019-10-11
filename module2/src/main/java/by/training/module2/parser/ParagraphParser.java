package by.training.module2.parser;

import by.training.module2.model.ParagraphComposite;
import by.training.module2.model.TextComposite;
import by.training.module2.model.TextLeaf;
import by.training.module2.model.WholeTextComposite;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphParser extends TextParser {
   // private final Pattern paragraphPattern = Pattern.compile("(\\t)(.+)(\\n+)?");
   private final Pattern paragraphPattern = Pattern.compile("(\\t|\\s)(.+)(\\n+)?");

    @Override
    public TextLeaf parse(String line) {
        TextComposite parsedText = new WholeTextComposite();
        Matcher matcher = paragraphPattern.matcher(line);
        while (matcher.find()) {
            ParagraphComposite paragraph = new ParagraphComposite();
            paragraph.addText(nextParse(matcher.group()));
            parsedText.addText(paragraph);
        }
        return parsedText;
    }

}
