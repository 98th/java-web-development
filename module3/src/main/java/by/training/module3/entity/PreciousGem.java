package by.training.module3.entity;

public class PreciousGem extends Gem {

    public PreciousGem(long id) {
        super(id, Preciousness.PRECIOUS);
    }

    public PreciousGem(int id, String name, String origin, VisualParameters visualParameters, double value) {
        super(id, name, Preciousness.PRECIOUS, origin, visualParameters, value);
    }

}
