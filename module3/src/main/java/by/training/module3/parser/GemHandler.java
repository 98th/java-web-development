package by.training.module3.parser;

import by.training.module3.entity.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class GemHandler extends DefaultHandler {
    private List<Gem> gems;
    private VisualParameters currVisualParameters;
    private Gem currentGem = null;
    private GemEnum currentEnum = null;

    GemHandler(){
        gems = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)  {
        currentEnum = GemEnum.valueOf(qName.toUpperCase());
        long id;
        switch (currentEnum) {
            case SEMIPRECIOUSGEM:
                id = Long.parseLong(attributes.getValue("id"));
                boolean isOrnamental = Boolean.parseBoolean(attributes.getValue("isUsedInOrnamentalWorks"));
                currentGem = new SemipreciousGem(id, isOrnamental);
                break;
            case PRECIOUSGEM:
                id = Long.parseLong(attributes.getValue("id"));
                double hardness = Double.parseDouble(attributes.getValue("id"));
                currentGem = new PreciousGem(id, hardness);
                break;
            case VISUALPARAMETERS:
                currVisualParameters = new VisualParameters();
                currentGem.setVisualParameters(currVisualParameters);
                break;
            default:
                break;
        }
    }

    public void endElement(String uri, String localName, String qName) {
        currentEnum = GemEnum.valueOf(qName.toUpperCase());
        switch (currentEnum) {
            case SEMIPRECIOUSGEM:
            case PRECIOUSGEM:
                gems.add(currentGem);
                currentGem = null;
                break;
        }
        currentEnum = null;
    }

    @Override
    public void characters(char[] ch, int start, int length)  {
        String str = new String(ch, start, length).trim();
        if (currentEnum != null) {
            switch (currentEnum) {
                case COLOR:
                    currentGem.getVisualParameters().setColor(str);
                    break;
                case NAME:
                    currentGem.setName(str);
                    break;
                case VALUE:
                    currentGem.setValue(Double.parseDouble(str));
                    break;
                case ORIGIN:
                    currentGem.setOrigin(str);
                    break;
                case FACETNUM:
                    currentGem.getVisualParameters().setFacetNum(Integer.parseInt(str));
                    break;
                case TRANSPARENCY:
                    currentGem.getVisualParameters().setTransparency(Double.parseDouble(str));
                    break;
                case PRECIOUSNESS:
                    currentGem.setPreciousness(Preciousness.valueOf(str));
                    break;
            }
            //currentGem = null;
        }

    }

    public List<Gem> getGems() {
        return gems;
    }
}
