package by.training.module3;

import by.training.module3.command.*;
import by.training.module3.controller.GemController;
import by.training.module3.entity.Gem;
import by.training.module3.repository.GemRepository;
import by.training.module3.repository.Repository;
import by.training.module3.service.GemService;
import by.training.module3.service.Service;
import by.training.module3.validator.FileValidator;
import by.training.module3.validator.XMLValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


@RunWith(JUnit4.class)
public class GemControllerTest {
    private GemController controller;
    private Service<Gem> service;
    private ClassLoader classLoader = getClass().getClassLoader();


    @Before
    public void init() {
        FileValidator fileValidator = new FileValidator();
        Repository<Gem> repository = new GemRepository();
        service = new GemService(repository);
        CommandProvider<Gem> commands = new ParserCommandProvider();
        Command SAXCommand = new SAXParserCommand();
        Command DOMCommand = new DOMParserCommand();
        commands.addCommand(CommandType.DOM, DOMCommand);
        commands.addCommand(CommandType.SAX, SAXCommand);
        String XSDPath = new File(classLoader.getResource("gems.xsd").getFile()).getAbsolutePath();
        XMLValidator xmlValidator = new XMLValidator(XSDPath);
        controller = new GemController(service, xmlValidator, fileValidator, commands);
    }

    @Test
    public void shouldExecuteValidFileSAX(){
        String XMLPath = new File(classLoader.getResource("gems_valid.xml").getFile()).getAbsolutePath();
        controller.execute(XMLPath, CommandType.SAX);
        assertEquals(16, service.getAll().size());
    }

    @Test
    public void shouldExecuteValidFileDOM(){
        String XMLPath = new File(classLoader.getResource("gems_valid.xml").getFile()).getAbsolutePath();
        controller.execute(XMLPath, CommandType.DOM);
        assertEquals(16, service.getAll().size());
    }

    @Test
    public void shouldExecuteInvalidFileDOM(){
        String XMLPath = new File(classLoader.getResource("gems_invalid.xml").getFile()).getAbsolutePath();

        assertThrows(Exception.class, () ->  controller.execute(XMLPath, CommandType.DOM));
    }

    @Test
    public void shouldExecuteInvalidFileSAX(){
        String XMLPath = new File(classLoader.getResource("gems_invalid.xml").getFile()).getAbsolutePath();
        assertThrows(Exception.class, () ->  controller.execute(XMLPath, CommandType.SAX));
    }
}
