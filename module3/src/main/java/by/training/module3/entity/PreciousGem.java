package by.training.module3.entity;

public class PreciousGem extends Gem {
    private double hardness;

    public PreciousGem(long id, double hardness) {
        super(id, Preciousness.PRECIOUS);
        this.hardness = hardness;
    }

    public PreciousGem(double hardness, long id, String name, String origin, VisualParameters visualParameters, double value) {
        super(id, name, Preciousness.PRECIOUS, origin, visualParameters, value);
        this.hardness = hardness;
    }

}
