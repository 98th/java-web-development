package by.training.module1.entity;

public class MilitaryAircraft extends Aircraft {
    private int MissileNumber;

    public MilitaryAircraft(String model, int passengers, int fuelConsumption, double loadCapacity,
                            int speed, int missileNumber) {
        super(AircraftType.MILITARY, model, passengers, fuelConsumption, loadCapacity, speed);
        MissileNumber = missileNumber;
    }

    public int getMissileNumber() {
        return MissileNumber;
    }

    public void setMissileNumber(int missileNumber) {
        MissileNumber = missileNumber;
    }
}