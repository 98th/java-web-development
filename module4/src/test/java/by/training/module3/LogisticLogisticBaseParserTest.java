package by.training.module3;

import by.training.module3.model.LogisticBase;
import by.training.module3.parser.LogisticBaseParser;
import by.training.module3.parser.Parser;
import by.training.module3.parser.ParserException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.Objects;

import static org.junit.Assert.assertEquals;


@RunWith(JUnit4.class)
public class LogisticLogisticBaseParserTest {
    private ClassLoader classLoader = getClass().getClassLoader();
    private Parser<LogisticBase> parser = new LogisticBaseParser();

    @Test
    public void shouldParseBase()  throws ParserException {
        String baseXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("base.xml")).getFile()).getAbsolutePath();
        LogisticBase logisticBase = parser.parse(baseXMLPath);
        assertEquals(logisticBase.getId(), 1);
        assertEquals(logisticBase.getItems().size(), 15);
        assertEquals(logisticBase.getTerminals().size(), 2);
    }
}
