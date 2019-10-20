package by.training.module3.parser;

import by.training.module3.entity.Gem;
import by.training.module3.entity.PreciousGem;
import by.training.module3.entity.SemipreciousGem;
import by.training.module3.entity.VisualParameters;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GemDOMParser implements Parser<Gem> {
    private static final Logger log = LogManager.getLogger(GemDOMParser.class);

    private DocumentBuilder documentBuilder;

    public GemDOMParser() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.error("exception on parser configuration" + e);
        }
    }

    @Override
    public List<Gem> parse (String path) throws ParserException {
        Document doc = null;
        List<Gem> output = new ArrayList<>();
        try {
            doc = documentBuilder.parse(path);
            Element root = doc.getDocumentElement();
            NodeList preciousGemList = root.getElementsByTagName("PreciousGem");
            for (int i = 0; i < preciousGemList.getLength(); i++) {
                Element element = (Element) preciousGemList.item(i);
                Gem gem = buildPreciousGem(element);
                output.add(gem);
            }
            NodeList semipreciousGemList = root.getElementsByTagName("SemipreciousGem");
            for (int i = 0; i < semipreciousGemList.getLength(); i++) {
                Element element = (Element) semipreciousGemList.item(i);
                Gem gem = buildSemipreciousGem(element);
                output.add(gem);
            }
        } catch (IOException e) {
            log.error("IOException" + e);
        } catch (SAXException e) {
            log.error("SAXException" + e);
        }
        return output;
    }

    private Gem buildPreciousGem(Element gemElement) {
        Gem gem = new PreciousGem(Long.valueOf(gemElement.getAttribute("id")));
        gem.setOrigin(getElementTextContent(gemElement, "origin")); // проверка на null
        gem.setName(getElementTextContent(gemElement, "name"));
        Double value = Double.valueOf(getElementTextContent(
                gemElement,"value"));
        gem.setValue(value);
        VisualParameters visualParameters = new VisualParameters();
        Element visualParametersElement = (Element) gemElement.getElementsByTagName("visualParameters").item(0);
        Double transparency = Double.valueOf(getElementTextContent(visualParametersElement, "transparency"));
        visualParameters.setTransparency(transparency);
        Integer facetNum = Integer.valueOf(getElementTextContent(visualParametersElement, "facetNum"));
        visualParameters.setFacetNum(facetNum);
        visualParameters.setColor(getElementTextContent(visualParametersElement, "color"));
        gem.setVisualParameters(visualParameters);
        return gem;
    }

    private Gem buildSemipreciousGem(Element gemElement) {
        Gem gem = new SemipreciousGem(Long.valueOf(gemElement.getAttribute("id")));
        gem.setOrigin(getElementTextContent(gemElement, "origin")); // проверка на null
        gem.setName(getElementTextContent(gemElement, "name"));
        Double value = Double.valueOf(getElementTextContent(
                gemElement,"value"));
        gem.setValue(value);
        VisualParameters visualParameters = new VisualParameters();
        Element visualParametersElement = (Element) gemElement.getElementsByTagName("visualParameters").item(0);
        Double transparency = Double.valueOf(getElementTextContent(visualParametersElement, "transparency"));
        visualParameters.setTransparency(transparency);
        Integer facetNum = Integer.valueOf(getElementTextContent(visualParametersElement, "facetNum"));
        visualParameters.setFacetNum(facetNum);
        visualParameters.setColor(getElementTextContent(visualParametersElement, "color"));
        gem.setVisualParameters(visualParameters);
        return gem;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        return  node.getTextContent();
    }
}
