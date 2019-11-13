package by.training.module1.entity;

public abstract class Aircraft {
    private AircraftType type;
    private String model;
    private int passengers;
    private int fuelConsumption;
    private double loadCapacity;
    private int speed;

    public Aircraft() {}

    public Aircraft(AircraftType type, String model, int passengers, int fuelConsumption, double loadCapacity, int speed) {
        this.type = type;
        this.model = model;
        this.passengers = passengers;
        this.fuelConsumption = fuelConsumption;
        this.loadCapacity = loadCapacity;
        this.speed = speed;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public AircraftType getType() {
        return type;
    }

    public void setType(AircraftType type) {
        this.type = type;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public int getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(int fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
