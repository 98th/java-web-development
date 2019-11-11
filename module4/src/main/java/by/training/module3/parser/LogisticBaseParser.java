package by.training.module3.parser;

import by.training.module3.model.LogisticBase;
import by.training.module3.model.Item;
import by.training.module3.model.Terminal;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LogisticBaseParser implements Parser<LogisticBase> {

    private static final Logger log = LogManager.getLogger(LogisticBaseParser.class);

    @Override
    public LogisticBase parse(String path) throws ParserException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader;
        List<Item> items = null;
        Queue<Terminal> terminals = null;
        LogisticBase logisticBase = null;
        try {
            reader = factory.createXMLStreamReader(new FileInputStream(path));
            while (reader.hasNext()) {
                int event = reader.next();
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        try {
                            switch (reader.getLocalName()) {
                                case "logisticBase":
                                    long id = Long.parseLong(reader.getAttributeValue(null, "id"));
                                    int capacity = Integer.parseInt(reader.getAttributeValue(null, "capacity"));
                                    logisticBase = LogisticBase.getInstance(id, capacity);
                                    break;
                                case "items":
                                    items = new ArrayList<>();
                                    break;
                                case "terminals":
                                    terminals = new LinkedList<>();
                                    break;
                                case "item":
                                    id = Long.parseLong(reader.getAttributeValue(null, "id"));
                                    items.add(new Item(id));
                                    break;
                                case "terminal":
                                    id = Long.parseLong(reader.getAttributeValue(null, "id"));
                                    terminals.add(new Terminal(id));
                                    break;
                                default:
                                    throw new ParserException("Incorrect data: " + reader.getLocalName());
                            }
                        } catch (IllegalArgumentException e) {
                            throw new ParserException(e);
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        if ("logisticBase".equals(reader.getLocalName())) {
                            logisticBase.addItems(items);
                            logisticBase.addTerminals(terminals);
                        }
                }
            }
        } catch (XMLStreamException | FileNotFoundException e) {
            throw new ParserException(e);
        }
        return logisticBase;
    }
}
