package by.training.module2;

import by.training.module2.model.TextLeaf;
import by.training.module2.parser.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ParserChainTest {
    private String text;
    private ParserChain<TextLeaf> parserChain;

    @Before
    public void init () {
        text = "\tIt has survived not only five centuries, but also the leap into electronic " +
                "typesetting, remaining essentially unchanged. It was popularised in the with the " +
                "release of Letraset sheets containing Lorem Ipsum passages, and more recently with " +
                "desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                "\tIt is a long established fact that a reader will be distracted by the readable " +
                "content of a page when looking at its layout. The point of using Ipsum is that it has a " +
                "more-or-less normal distribution of letters, as opposed to using 'Content here, content " +
                "here', making it look like readable English.\n" +
                "\tIt is a established fact that a reader will be of a page when looking at its " +
                "layout.\n" +
                "\tBye.";
    }

    @Test
    public void shouldParseWords () {
        parserChain = new WordParser();
        List<TextLeaf> parsedText = new ArrayList<>(parserChain.parse(text));
        assertEquals(123, parsedText.size());
    }

    @Test
    public void shouldParseSentences () {
        parserChain = new WordParser().linkWith(new SentenceParser());
        List<TextLeaf> parsedText = new ArrayList<>(parserChain.parse(text));
        assertEquals(6, parsedText.size());
    }

    @Test
    public void shouldParseParagraphs () {
        parserChain = new WordParser().linkWith(new SentenceParser()).linkWith(new ParagraphParser());
        List<TextLeaf> parsedText = new ArrayList<>(parserChain.parse(text));
        assertEquals(4, parsedText.size());
    }
}
