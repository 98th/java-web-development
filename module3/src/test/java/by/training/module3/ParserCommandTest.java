package by.training.module3;

import by.training.module3.command.Command;
import by.training.module3.command.CommandException;
import by.training.module3.command.DOMParserCommand;
import by.training.module3.command.SAXParserCommand;
import by.training.module3.entity.Gem;
import by.training.module3.parser.GemDOMParser;
import by.training.module3.validator.XMLValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(JUnit4.class)
public class ParserCommandTest {
    private XMLValidator validator;
    private ClassLoader classLoader = getClass().getClassLoader();
    private Command<Gem> command;

    @Before
    public void init () {
        String XSDPath = new File(classLoader.getResource("gems.xsd").getFile()).getAbsolutePath();
        validator = new XMLValidator(XSDPath);
    }

    @Test
    public void shouldParseValidFileSAX() throws CommandException  {
        String XMLPath = new File(classLoader.getResource("gems_valid.xml").getFile()).getAbsolutePath();
        command = new SAXParserCommand();
        List<Gem> result = command.parse(XMLPath);
        assertEquals(16, result.size());
    }

    @Test
    public void shouldParseValidFileDOM() throws CommandException  {
        String XMLPath = new File(classLoader.getResource("gems_valid.xml").getFile()).getAbsolutePath();
        command = new DOMParserCommand();
        List<Gem> result = command.parse(XMLPath);
        assertEquals(16, result.size());
    }

}
