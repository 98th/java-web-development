package by.training.module2.parser;

import by.training.module2.model.TextLeaf;
import by.training.module2.model.WordLeaf;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordParser extends TextParser{
    private final Pattern wordPattern = Pattern.compile("([\\'\\(]+)?(\\w+((-)*\\w)*)([\\.\\,\\'\\)\\:]+)?");


    @Override
    public List<TextLeaf> parse(String line) {
        List<TextLeaf> words = new LinkedList<>();
        Matcher matcher = wordPattern.matcher(line);
      //  long id = 1;
        while(matcher.find()) {
            words.add(new WordLeaf(matcher.group(2), matcher.group(5)));
        }
        return words;
    }

}
