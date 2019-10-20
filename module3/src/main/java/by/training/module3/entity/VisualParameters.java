package by.training.module3.entity;

public class VisualParameters {
    private String color;
    private double transparency;
    private int facetNum;

    public VisualParameters() {
    }

    public VisualParameters(String color, double transparency, int facetNum) {
        this.color = color;
        this.transparency = transparency;
        this.facetNum = facetNum;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getTransparency() {
        return transparency;
    }

    public void setTransparency(double transparency) {
        this.transparency = transparency;
    }

    public int getFacetNum() {
        return facetNum;
    }

    public void setFacetNum(int facetNum) {
        this.facetNum = facetNum;
    }
}
