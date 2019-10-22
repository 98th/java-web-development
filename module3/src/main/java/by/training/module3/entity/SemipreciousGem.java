package by.training.module3.entity;

public class SemipreciousGem extends Gem {
    private boolean isUsedInOrnamentalWorks;

    public SemipreciousGem(long id, boolean isUsedInOrnamentalWorks) {
        super(id, Preciousness.SEMIPRECIOUS);
        this.isUsedInOrnamentalWorks = isUsedInOrnamentalWorks;
    }

    public SemipreciousGem(boolean isUsedInOrnamentalWorks,long id, String name, String origin,
                           VisualParameters visualParameters, double value) {
        super(id, name, Preciousness.SEMIPRECIOUS, origin, visualParameters, value);
        this.isUsedInOrnamentalWorks = isUsedInOrnamentalWorks;
    }
}
