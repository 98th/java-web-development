package by.training.module2;

import by.training.module2.controller.DataReader;
import by.training.module2.model.*;
import by.training.module2.parser.*;
import by.training.module2.repository.TextRepository;
import by.training.module2.service.ParagraphService;
import by.training.module2.service.SentenceService;
import by.training.module2.service.WordService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;


import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ParserChainTest {
    private String text;
    private ParserChain<TextLeaf> parserChain;
    private ClassLoader classLoader = getClass().getClassLoader();
    private String expectedForWords = "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. " +
            "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. " +
            "It is a established fact that a reader will be of a page when looking at its layout. Bye. ";
    private String expectedForSentences = "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. " +
            "It was popularised in the with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. " +
            "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. " +
            "The point of using Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. " +
            "It is a established fact that a reader will be of a page when looking at its layout. " +
            "Bye. ";
    private String expectedForParagraphs = "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. " +
            "It was popularised in the with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. \n" +
            "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. " +
            "The point of using Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. \n" +
            "It is a established fact that a reader will be of a page when looking at its layout. \n" +
            "Bye. ";
    @Before
    public void init () {
        String path = new File(classLoader.getResource("PerfectFile.txt").getFile()).getAbsolutePath();
        DataReader dataReader = new DataReader();
        text = dataReader.read(path);
    }


    @Test
    public void shouldParseWords () {
        parserChain = new WordParser();
        String parsedText = parserChain.parse(text).getText();
        assertEquals(expectedForWords, parsedText);
    }

    @Test
    public void shouldParseSentences () {
        parserChain = new WordParser().linkWith(new SentenceParser());
        String parsedText = parserChain.parse(text).getText();
        assertEquals(expectedForSentences, parsedText);
    }

    @Test
    public void shouldParseParagraphs () {
        parserChain = new WordParser()
                .linkWith(new SentenceParser())
                .linkWith(new ParagraphParser());
        String parsedText = parserChain.parse(text).getText();
        assertEquals(expectedForParagraphs, parsedText);
    }
}
