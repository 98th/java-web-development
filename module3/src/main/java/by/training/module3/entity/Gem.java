package by.training.module3.entity;

public class Gem {
    private int id;
    private String name;
    private Preciousness preciousness;
    private String origin;
    private VisualParameters visualParameters;
    private double value;

    public Gem(int id, String name, Preciousness preciousness, String origin, VisualParameters visualParameters, double value) {
        this.id = id;
        this.name = name;
        this.preciousness = preciousness;
        this.origin = origin;
        this.visualParameters = visualParameters;
        this.value = value;
    }


}
