package by.training.module3;

import by.training.module3.command.DOMParserCommand;
import by.training.module3.command.ParserCommand;
import by.training.module3.command.SAXParserCommand;
import by.training.module3.entity.Gem;
import by.training.module3.validator.XMLValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


@RunWith(JUnit4.class)
public class ParserCommandTest {
    private XMLValidator validator;
    private ClassLoader classLoader = getClass().getClassLoader();
    private ParserCommand<Gem> command;

    @Before
    public void init () {
        String XSDPath = new File(classLoader.getResource("gems.xsd").getFile()).getAbsolutePath();
        validator = new XMLValidator(XSDPath);
    }

    @Test
    public void shouldParseValidFileSAX() {
        String XMLPath = new File(classLoader.getResource("gems_valid.xml").getFile()).getAbsolutePath();
        command = new SAXParserCommand(validator);
        List<Gem> result = command.build(XMLPath);
        assertEquals(16, result.size());
    }

    @Test
    public void shouldParseInvalidFileSAX() {
        String XMLPath = new File(classLoader.getResource("gems_invalid.xml").getFile()).getAbsolutePath();
        command = new SAXParserCommand(validator);
        assertThrows(Exception.class, () -> command.build(XMLPath));
    }

    @Test
    public void shouldParseValidFileDOM() {
        String XMLPath = new File(classLoader.getResource("gems_valid.xml").getFile()).getAbsolutePath();
        command = new DOMParserCommand(validator);
        List<Gem> result = command.build(XMLPath);
        assertEquals(16, result.size());
    }

    @Test
    public void shouldParseInvalidFileDOM() {
        String XMLPath = new File(classLoader.getResource("gems_invalid.xml").getFile()).getAbsolutePath();
        command = new DOMParserCommand(validator);
        assertThrows(Exception.class, () -> command.build(XMLPath));
    }
}
