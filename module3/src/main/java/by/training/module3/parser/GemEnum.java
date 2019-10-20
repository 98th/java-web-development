package by.training.module3.parser;

public enum  GemEnum {
    GEMS("gems"),
    SEMIPRECIOUSGEM("SemipreciousGem"),
    PRECIOUSGEM("PreciousGem"),
    VISUALPARAMETERS("visualParameters"),
    ID("id"),
    COLOR("color"),
    PRECIOUSNESS("preciousness"),
    TRANSPARENCY("transparency"),
    FACETNUM("facetNum"),
    VALUE("value"),
    ORIGIN("origin"),
    NAME("name");
    private String value;
    GemEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
