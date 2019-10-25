package by.training.module3.entity;

import java.util.Date;

public class SemipreciousGem extends Gem {
    private boolean isUsedInOrnamentalWorks;

    public SemipreciousGem(long id, boolean isUsedInOrnamentalWorks) {
        super(id, Preciousness.SEMIPRECIOUS);
        this.isUsedInOrnamentalWorks = isUsedInOrnamentalWorks;
    }

    public SemipreciousGem(boolean isUsedInOrnamentalWorks,long id, String name, String origin,
                           VisualParameters visualParameters, double value, Date miningDate) {
        super(id, name, Preciousness.SEMIPRECIOUS, origin, visualParameters, value, miningDate);
        this.isUsedInOrnamentalWorks = isUsedInOrnamentalWorks;
    }
}
