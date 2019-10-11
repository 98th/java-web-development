package by.training.module2.parser;

import by.training.module2.model.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordParser extends TextParser{
    private final Pattern wordPattern = Pattern.compile("([\\'\\(]+)?(\\w+((-)*\\w)*)([\\!\\?\\.\\,\\'\\)\\:]+)?");

    @Override
    public TextLeaf parse(String line) {
        boolean isNewLine = line.charAt(0) == '\n';
        TextComposite sentenceComposite = new SentenceComposite(isNewLine);
        Matcher matcher = wordPattern.matcher(line);
        while(matcher.find()) {
            sentenceComposite.addText(new WordLeaf(matcher.group(1), matcher.group(2), matcher.group(5)));
        }
        return sentenceComposite;
    }

}
