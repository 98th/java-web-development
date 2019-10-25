package by.training.module3;

import by.training.module3.command.*;
import by.training.module3.entity.Gem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(JUnit4.class)
public class ParserCommandTest {
    private ClassLoader classLoader = getClass().getClassLoader();
    private Command<Gem> command;


    @Test
    public void shouldParseValidFileSAX() throws CommandException  {
        String XMLPath = new File(classLoader.getResource("gems_valid.xml").getFile()).getAbsolutePath();
        command = new SAXParserCommand();
        List<Gem> result = command.parse(XMLPath);
        assertEquals(16, result.size());
    }

    @Test
    public void shouldParseValidFileStAX() throws CommandException  {
        String XMLPath = new File(classLoader.getResource("gems_valid.xml").getFile()).getAbsolutePath();
        command = new StAXParserCommand();
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
