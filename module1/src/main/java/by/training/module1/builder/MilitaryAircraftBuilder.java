package by.training.module1.builder;

import by.training.module1.entity.Aircraft;
import by.training.module1.entity.MilitaryAircraft;

import java.util.Map;

public class MilitaryAircraftBuilder implements AircraftBuilder {


    @Override
    public Aircraft build (Map<String, String> aircraft) {
        String model = aircraft.get("model");
        int passengers = Integer.parseInt(aircraft.get("passengers"));
        int fuelConsumption = Integer.parseInt(aircraft.get("fuelConsumption"));
        double loadCapacity = Double.parseDouble(aircraft.get("loadCapacity"));
        int speed = Integer.parseInt(aircraft.get("speed"));
        int missileNumber = Integer.parseInt(aircraft.get("missileNumber"));

        return new MilitaryAircraft(model, passengers, fuelConsumption, loadCapacity,
                speed, missileNumber);
    }
}
