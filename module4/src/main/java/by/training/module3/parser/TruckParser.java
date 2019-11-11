package by.training.module3.parser;

import by.training.module3.model.*;
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

public class TruckParser implements Parser<List<Truck>> {

    private static final Logger log = LogManager.getLogger(TruckParser.class);

    @Override
    public List<Truck> parse(String path) throws ParserException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader;
        List<Truck> output = null;
        Truck truck = null;
        List<Item> itemList = null;
        try {
            reader = factory.createXMLStreamReader(new FileInputStream(path));
            while (reader.hasNext()) {
                int event = reader.next();
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        try {
                            switch (reader.getLocalName()) {
                                case "trucks":
                                    output = new LinkedList<>();
                                    break;
                                case "truck":
                                    long id = Long.parseLong(reader.getAttributeValue(null, "id"));
                                    int capacity = Integer.parseInt(reader.getAttributeValue(null, "capacity"));
                                    boolean isCargoPerishable = Boolean.parseBoolean(reader.getAttributeValue(null,
                                            "isCargoPerishable"));
                                    String operationType = reader.getAttributeValue(null,
                                            "operationType");
                                    truck = new Truck(id, capacity, isCargoPerishable, OperationType.getOperationByValue(operationType));
                                    break;
                                case "itemList":
                                    itemList = new ArrayList<>();
                                    break;
                                case "item":
                                    id = Long.parseLong(reader.getAttributeValue(null, "id"));
                                    itemList.add(new Item(id));
                                    break;
                                default:
                                    throw new ParserException("Incorrect data: " + reader.getLocalName());
                            }
                        } catch (IllegalArgumentException e) {
                            throw new ParserException(e);
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        if ("itemList".equals(reader.getLocalName())) {
                            truck.addItems(itemList);
                            break;
                        }
                        if ("truck".equals(reader.getLocalName())) {
                            output.add(truck);
                            break;
                        }
                }
            }
        } catch (XMLStreamException | FileNotFoundException e) {
            throw new ParserException(e);
        }
        return output;
    }
}
