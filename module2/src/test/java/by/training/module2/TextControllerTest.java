package by.training.module2;

import by.training.module2.controller.DataReader;
import by.training.module2.controller.TextController;
import by.training.module2.model.TextLeaf;
import by.training.module2.parser.ParagraphParser;
import by.training.module2.parser.ParserChain;
import by.training.module2.parser.SentenceParser;
import by.training.module2.parser.WordParser;
import by.training.module2.repository.WordLeafRepository;
import by.training.module2.service.WordLeafService;
import by.training.module2.validator.FileValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TextControllerTest {
    private TextController controller;
    private ClassLoader classLoader = getClass().getClassLoader();
    private String path = new File(classLoader.getResource("PerfectFile.txt").getFile()).getAbsolutePath();
    private DataReader dataReader;

    @Before
    public void init () {
        dataReader = new DataReader();
        WordLeafRepository wordLeafRepository = new WordLeafRepository();
        WordLeafService service = new WordLeafService(wordLeafRepository);
        FileValidator fileValidator = new FileValidator();
        ParserChain<TextLeaf> parserChain = new WordParser().linkWith(new SentenceParser()).linkWith(new ParagraphParser());
        controller = new TextController(dataReader, service, fileValidator, parserChain);
    }

    @Test
    public void controllerTest () {
        String beginning = "\tIt has survived not only five centuries,";
        String ending = "\tBye. ";
        assertEquals(true, controller.composeText(controller.parseText(path)).getText().startsWith(beginning));
        assertEquals(true, controller.composeText(controller.parseText(path)).getText().endsWith(ending));
    }
}
