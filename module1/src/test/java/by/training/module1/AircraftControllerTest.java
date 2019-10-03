package by.training.module1;


import by.training.module1.builder.AircraftBuilderFactory;
import by.training.module1.controller.AircraftController;
import by.training.module1.controller.DataReader;
import by.training.module1.parser.LineParser;
import by.training.module1.repository.AircraftRepository;
import by.training.module1.service.AircraftService;
import by.training.module1.validator.FileValidator;
import by.training.module1.validator.LineValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;

@RunWith(JUnit4.class)
public class AircraftControllerTest {
    private AircraftController controller;
    private AircraftService service;
    private ClassLoader classLoader = getClass().getClassLoader();


    @Before
    public void init () {
        service = new AircraftService(new AircraftRepository());
        controller = new AircraftController(new DataReader(), service, new FileValidator(), new LineParser(),
                new AircraftBuilderFactory(), new LineValidator());
    }

    @Test
    public void testPerfectFile() {
        String path = new File(classLoader.getResource("PerfectFile.txt").getFile()).getAbsolutePath();
        //controller.execute(Path.get(this.getClass().getResource("/PerfectFile.txt")).toString());
        controller.execute(path);
        Assert.assertEquals(7, service.getAll().size());
    }

    @Test
    public void testAlmostPerfectFile() {
        String path = new File(classLoader.getResource("AlmostPerfectFile.txt").getFile()).getAbsolutePath();
        controller.execute(path);
        Assert.assertEquals(6, service.getAll().size() );
    }

    @Test
    public void testEmptyFile() {
        String path = new File(classLoader.getResource("EmptyFile.txt").getFile()).getAbsolutePath();
        controller.execute(path);
        Assert.assertEquals(0, service.getAll().size() );
    }

    @Test
    public void testInvalidFile() {
        String path = new File(classLoader.getResource("InvalidFile.txt").getFile()).getAbsolutePath();
        controller.execute(path);
        Assert.assertEquals(0, service.getAll().size() );
    }

    @Test
    public void testFileWithSomeEmptyLines() {
        String path = new File(classLoader.getResource("FileWithEmptyLines.txt").getFile()).getAbsolutePath();
        controller.execute(path);
        Assert.assertEquals(7, service.getAll().size() );
    }
}
