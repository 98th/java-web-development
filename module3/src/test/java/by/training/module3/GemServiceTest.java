package by.training.module3;

import by.training.module3.command.*;
import by.training.module3.controller.GemController;
import by.training.module3.entity.Gem;
import by.training.module3.entity.PreciousGem;
import by.training.module3.entity.SemipreciousGem;
import by.training.module3.entity.VisualParameters;
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

@RunWith(JUnit4.class)
public class GemServiceTest {
    private Service<Gem> service;
    private ClassLoader classLoader = getClass().getClassLoader();

    @Before
    public void init() {
        String XSDPath = new File(classLoader.getResource("gems.xsd").getFile()).getAbsolutePath();
        XMLValidator xmlValidator = new XMLValidator(XSDPath);
        FileValidator fileValidator = new FileValidator();
        Repository<Gem> repository = new GemRepository();
        service = new GemService(repository);
    }


    @Test
    public void shouldAddSomeGems () {
        VisualParameters visualParameters1 = new VisualParameters("green", 25.5, 8);
        Gem gem1 = new PreciousGem(1, "Emerald", "columbia", visualParameters1, 2.5 );
        VisualParameters visualParameters2 = new VisualParameters("blue", 2.67, 6);
        Gem gem2 = new SemipreciousGem(2,"Ametrine", "Colombia", visualParameters2, 2.0);
        service.add(gem1);
        service.add(gem2);
        assertEquals(2, service.getAll().size());
    }

    @Test
    public void shouldReturnById () {
        VisualParameters visualParameters1 = new VisualParameters("green", 25.5, 8);
        Gem gem1 = new PreciousGem(1, "Emerald", "columbia", visualParameters1, 2.5 );
        VisualParameters visualParameters2 = new VisualParameters("blue", 2.67, 6);
        Gem gem2 = new SemipreciousGem(2,"Ametrine", "Colombia", visualParameters2, 2.0);
        service.add(gem1);
        service.add(gem2);
        assertEquals("Ametrine", service.get(2).get().getName());
        assertEquals("Emerald", service.get(1).get().getName());
    }

}