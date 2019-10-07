package by.training.module2;

import by.training.module2.controller.DataReader;
import by.training.module2.model.TextLeaf;
import by.training.module2.parser.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ParserChainTest {
    private String text;
    private ParserChain<TextLeaf> parserChain;
    private ClassLoader classLoader = getClass().getClassLoader();

    @Before
    public void init () {
        String path = new File(classLoader.getResource("PerfectFile.txt").getFile()).getAbsolutePath();
        DataReader dataReader = new DataReader();
        text = dataReader.read(path);
    }

    @Test
    public void shouldParseWords () {
        parserChain = new WordParser();
        List<TextLeaf> parsedText = new ArrayList<>(parserChain.parse(text));
        assertEquals(119, parsedText.size());
    }

    @Test
    public void shouldParseSentences () {
        parserChain = new WordParser().linkWith(new SentenceParser());
        List<TextLeaf> parsedText = new ArrayList<>(parserChain.parse(text));
        parsedText.stream().map(i -> i.getText()).forEach(System.out::println);
        assertEquals(6, parsedText.size());
    }

    @Test
    public void shouldParseParagraphs () {
        parserChain = new WordParser().linkWith(new SentenceParser()).linkWith(new ParagraphParser());
        List<TextLeaf> parsedText = new ArrayList<>(parserChain.parse(text));
        parsedText.stream().map(i -> i.getText()).forEach(System.out::println);
        assertEquals(4, parsedText.size());
    }
}
