package by.training.module3;

import by.training.module3.validator.XMLValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


@RunWith(JUnit4.class)
public class ValidatorTest {
    private XMLValidator validator;
    private ClassLoader classLoader = getClass().getClassLoader();

    @Before
    public void init() {
        String XSDPath = new File(classLoader.getResource("gems.xsd").getFile()).getAbsolutePath();
        validator = new XMLValidator(XSDPath);
    }

    @Test
    public void shouldTestPerfectFile() {
        String XMLPath = new File(classLoader.getResource("gems.xml").getFile()).getAbsolutePath();
        assertTrue(validator.validateXML(XMLPath));
    }

    @Test
    public void shouldTestInvalidFile() {
        String XMLPath = new File(classLoader.getResource("gems_invalid.xml").getFile()).getAbsolutePath();
        assertFalse(validator.validateXML(XMLPath));
    }

}
