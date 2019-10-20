package by.training.module3.entity;

public class SemipreciousGem extends Gem {

    public SemipreciousGem(long id) {
        super(id, Preciousness.SEMIPRECIOUS);
    }

    public SemipreciousGem(int id, String name, String origin, VisualParameters visualParameters, double value) {
        super(id, name, Preciousness.SEMIPRECIOUS, origin, visualParameters, value);
    }
}
