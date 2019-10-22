package by.training.module3.parser;

import by.training.module3.entity.Gem;
import by.training.module3.entity.PreciousGem;
import by.training.module3.entity.SemipreciousGem;
import by.training.module3.entity.VisualParameters;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static by.training.module3.parser.GemEnum.*;

public class GemStAXParser implements Parser<Gem> {
    private static final Logger log = LogManager.getLogger(GemStAXParser.class);

    @Override
    public List<Gem> parse (String path) throws ParserException {
        XMLInputFactory  inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader;
        List<Gem> output = new ArrayList<>();
        String name;
        try (FileInputStream inputStream = new FileInputStream(new File(path))) {
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (SEMIPRECIOUSGEM.getValue().equalsIgnoreCase(name)) {
                        Gem  gem = buildSemipreciousGem(reader);
                        output.add(gem);
                    } else if (PRECIOUSGEM.getValue().equalsIgnoreCase(name)) {
                        Gem gem = buildPreciousGem(reader);
                        output.add(gem);
                    }
                }
            }
        } catch (XMLStreamException | IOException e) {
            throw new ParserException(e);
        }
        return output;
    }

    private Gem buildSemipreciousGem (XMLStreamReader reader) throws XMLStreamException{
        long id = Long.parseLong(reader.getAttributeValue(null, "id"));
        boolean isOrnamental = Boolean.parseBoolean(reader.getAttributeValue(null, "isUsedInOrnamentalWorks"));
        Gem gem = new SemipreciousGem(id, isOrnamental);
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (GemEnum.valueOf(name.toUpperCase())) {
                        case NAME:
                            gem.setName(getXMLText(reader));
                            break;
                        case ORIGIN:
                            gem.setOrigin(getXMLText(reader));
                            break;
                        case VALUE:
                            name = getXMLText(reader);
                            gem.setValue(Double.parseDouble(name));
                            break;
                        case VISUALPARAMETERS:
                            gem.setVisualParameters(getXMLVisualParameters(reader));
                            break;
                        default:
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (SEMIPRECIOUSGEM.getValue().equalsIgnoreCase(name)) {
                        return gem;
                    }
                    break;
            }
        }
        throw new XMLStreamException();
    }

    private VisualParameters getXMLVisualParameters (XMLStreamReader reader) throws XMLStreamException {
        VisualParameters visualParameters = new VisualParameters();
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (GemEnum.valueOf(name.toUpperCase())) {
                        case COLOR:
                            visualParameters.setColor(getXMLText(reader));
                            break;
                        case TRANSPARENCY:
                            name = getXMLText(reader);
                            visualParameters.setTransparency(Double.parseDouble(name));
                            break;
                        case FACETNUM:
                            name = getXMLText(reader);
                            visualParameters.setFacetNum(Integer.parseInt(name));
                            break;
                        default:
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (VISUALPARAMETERS.getValue().equalsIgnoreCase(name)){
                        return visualParameters;
                    }
                    break;
            }
        }
        log.error("cannot parse visual parameters");
        throw new XMLStreamException();
    }


    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }

    private Gem buildPreciousGem (XMLStreamReader reader) throws XMLStreamException{
        long id = Long.parseLong(reader.getAttributeValue(null, "id"));
        double hardness = Double.parseDouble(reader.getAttributeValue(null, "hardness"));
        Gem gem = new PreciousGem(id, hardness);
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (GemEnum.valueOf(name.toUpperCase())) {
                        case NAME:
                            gem.setName(getXMLText(reader));
                            break;
                        case ORIGIN:
                            gem.setOrigin(getXMLText(reader));
                            break;
                        case VALUE:
                            name = getXMLText(reader);
                            gem.setValue(Double.parseDouble(name));
                            break;
                        case VISUALPARAMETERS:
                            gem.setVisualParameters(getXMLVisualParameters(reader));
                            break;
                        default:
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (GemEnum.valueOf(name.toUpperCase()) == PRECIOUSGEM) {
                        return gem;
                    }
                    break;
            }
        }
        log.error("cannot parse semiprecious gem");
        throw new XMLStreamException();
    }

    private void isValid (String str) {
        if (str == null || str.isEmpty()) {
            throw  new IllegalArgumentException();
        }
    }

}
