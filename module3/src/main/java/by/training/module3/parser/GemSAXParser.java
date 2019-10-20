package by.training.module3.parser;

import by.training.module3.entity.Gem;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import java.io.IOException;
import java.util.List;

public class GemSAXParser implements Parser<Gem>  {
    private static final Logger log = LogManager.getLogger(GemSAXParser.class);
    private List<Gem> gems;
    private GemHandler sh;
    private XMLReader reader;
    public GemSAXParser() {
        sh = new GemHandler();
        try {
            reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(sh);
        } catch (Exception e) {
            log.error("Exception in SAX parser" + e);
        }
    }

    @Override
    public List<Gem> parse(String fileName) throws ParserException {
        try {
            reader.parse(fileName);
        } catch (SAXException e) {
            log.error("Exception in SAX parser" + e);
            throw new ParserException(e);
        } catch (IOException e) {
            log.error("IOException" + e);
            throw new ParserException(e);
        }
        gems = sh.getGems();
        return gems;
    }
}
