package by.training.module3;

import by.training.module3.model.Truck;
import by.training.module3.parser.Parser;
import by.training.module3.parser.ParserException;
import by.training.module3.parser.TruckParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TruckParserTest {
    private ClassLoader classLoader = getClass().getClassLoader();
    private Parser<List<Truck>> parser = new TruckParser();

    @Test
    public void shouldParseTruckList()  throws ParserException {
        String baseXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("trucks.xml")).getFile()).getAbsolutePath();
        List<Truck> truckQueue = parser.parse(baseXMLPath);
        assertEquals(truckQueue.size(),5);
    }
}