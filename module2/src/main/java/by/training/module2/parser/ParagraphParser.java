package by.training.module2.parser;

import by.training.module2.model.ParagraphComposite;
import by.training.module2.model.TextComposite;
import by.training.module2.model.TextLeaf;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphParser extends TextParser {
    private final Pattern paragraphPattern = Pattern.compile("(\\t)(.+)(\\n+)?");

    @Override
    public List<TextComposite> parse(String line) {
        List<TextComposite> output = new LinkedList<>();
        List<String> lines = new ArrayList<>();
        Matcher matcher = paragraphPattern.matcher(line);
        while (matcher.find()) {
            lines.add(matcher.group());
        }
        for (String i : lines) {
            TextComposite paragraph = new ParagraphComposite();
            for (TextLeaf j : nextParse(i)) {
                paragraph.addText(j);
            }
            output.add(paragraph);
        }
        return output;
    }

/*
    @Override
    public TextLeaf parse(String line) {
        List<String> lines = new ArrayList<>();

        Matcher matcher = paragraphPattern.matcher(line);
        while (matcher.find()) {
            lines.add(matcher.group());
        }
        List<TextLeaf> sentences = new ArrayList<>();
        for (String i : lines) {
            TextComposite sentenceComposite = new SentenceComposite();
            for (TextLeaf j : nextParse(i)) {
                sentenceComposite.addText(j);
                sentences.add(sentenceComposite);
            }
            //output.add(paragraph);
        }
        return new ParagraphComposite(sentences);
    }

 */
}
