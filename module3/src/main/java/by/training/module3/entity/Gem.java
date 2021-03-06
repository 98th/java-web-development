package by.training.module3.entity;

import java.util.Date;

public class Gem {
    private long id;
    private String name;
    private Preciousness preciousness;
    private String origin;
    private VisualParameters visualParameters;
    private double value;
    private Date miningDate;

    public Gem(long id, Preciousness preciousness) {
        this.id = id;
        this.preciousness = preciousness;
    }

    public Gem(long id, String name, Preciousness preciousness, String origin, VisualParameters visualParameters,
               double value, Date miningDate) {
        this.id = id;
        this.name = name;
        this.preciousness = preciousness;
        this.origin = origin;
        this.visualParameters = visualParameters;
        this.value = value;
        this.miningDate = miningDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Preciousness getPreciousness() {
        return preciousness;
    }

    public Date getMiningDate() {
        return miningDate;
    }

    public void setMiningDate(Date miningDate) {
        this.miningDate = miningDate;
    }

    public void setPreciousness(Preciousness preciousness) {
        this.preciousness = preciousness;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public VisualParameters getVisualParameters() {
        return visualParameters;
    }

    public void setVisualParameters(VisualParameters visualParameters) {
        this.visualParameters = visualParameters;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
