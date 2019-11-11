package by.training.module3;

import by.training.module3.controller.LogisticBaseController;
import by.training.module3.model.*;
import by.training.module3.parser.LogisticBaseParser;
import by.training.module3.parser.Parser;
import by.training.module3.parser.TruckParser;
import by.training.module3.validator.FileValidator;
import by.training.module3.validator.XMLValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;


@RunWith(JUnit4.class)
public class LogisticBaseControllerTest {
    private ClassLoader classLoader = getClass().getClassLoader();
    private LogisticBaseController controller;

    @Before
    public void init(){
        String trucksXSDPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("trucks.xsd")).getFile()).getAbsolutePath();
        FileValidator fileValidator = new FileValidator();
        XMLValidator xmlValidator = new XMLValidator(trucksXSDPath);
        Parser<List<Truck>> truckParser = new TruckParser();
        Parser<LogisticBase> baseParser = new LogisticBaseParser();
        controller = new LogisticBaseController(fileValidator,  xmlValidator, truckParser, baseParser);
    }

    @Test
    public void shouldExecuteTask () throws InterruptedException {
        String trucksXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("trucks.xml")).getFile()).getAbsolutePath();
        String baseXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("base.xml")).getFile()).getAbsolutePath();
        List<Truck> trucks = controller.execute(trucksXMLPath, baseXMLPath);
        assertEquals(5, trucks.size());
        TimeUnit.MILLISECONDS.sleep(5000);
    }
}
