package by.training.module2;

import by.training.module2.controller.DataReader;
import by.training.module2.controller.TextController;
import by.training.module2.model.TextComposite;
import by.training.module2.model.TextLeaf;
import by.training.module2.parser.ParagraphParser;
import by.training.module2.parser.ParserChain;
import by.training.module2.parser.SentenceParser;
import by.training.module2.parser.WordParser;
import by.training.module2.repository.ParagraphRepository;
import by.training.module2.repository.WordRepository;
import by.training.module2.service.TextService;
import by.training.module2.service.WordService;
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
    private String text;
    private TextService<TextComposite> service;
    private ParserChain<TextLeaf> parserChain;
    private ClassLoader classLoader = getClass().getClassLoader();
    private FileValidator fileValidator;
    private String path;


    @Before
    public void init () {
        path = new File(classLoader.getResource("PerfectFile.txt").getFile()).getAbsolutePath();
        DataReader dataReader = new DataReader();
        ParagraphRepository repository = new ParagraphRepository();
        service = new TextService<>(repository);
        fileValidator = new FileValidator();
        text = dataReader.read(path);
        parserChain = new WordParser().linkWith(new SentenceParser()).linkWith(new ParagraphParser());
        controller = new TextController(dataReader, service, fileValidator, parserChain);
    }

    @Test
    public void shouldSaveText() {
        controller.save(path);
        assertEquals(1, service.getAll().size());
    }
}
