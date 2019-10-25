package by.training.module3.entity;

import java.util.Date;

public class PreciousGem extends Gem {
    private double hardness;

    public PreciousGem(long id, double hardness) {
        super(id, Preciousness.PRECIOUS);
        this.hardness = hardness;
    }

    public PreciousGem(double hardness, long id, String name, String origin, VisualParameters visualParameters,
                       double value, Date miningDate) {
        super(id, name, Preciousness.PRECIOUS, origin, visualParameters, value, miningDate);
        this.hardness = hardness;
    }

}
