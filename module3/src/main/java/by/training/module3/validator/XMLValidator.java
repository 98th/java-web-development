package by.training.module3.validator;



import jdk.internal.org.xml.sax.SAXException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

public class XMLValidator {
    private String XSDPath;

    public XMLValidator(String XSDPath) {
        this.XSDPath = XSDPath;
    }

    public boolean validateXML(String XMLPath) {
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(XSDPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(XMLPath)));
            return true;
        } catch (Exception e) {
            return  false;
        }
    }
}
