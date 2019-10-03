package by.training.module1.builder;


import by.training.module1.entity.Aircraft;
import by.training.module1.entity.MedicalAircraft;

import java.util.Map;

public class MedicalAircraftBuilder  implements AircraftBuilder {


    @Override
    public Aircraft build (Map<String, String> aircraft) {
        String model = aircraft.get("model");
        int passengers = Integer.parseInt(aircraft.get("passengers"));
        int fuelConsumption = Integer.parseInt(aircraft.get("fuelConsumption"));
        double loadCapacity = Double.parseDouble(aircraft.get("loadCapacity"));
        int speed = Integer.parseInt(aircraft.get("speed"));
        boolean resuscitationEquipment = Boolean.parseBoolean(aircraft.get("resuscitationEquipment"));
        int firstAidKits = Integer.parseInt(aircraft.get("firstAidKits"));

        return new MedicalAircraft(model, passengers, fuelConsumption, loadCapacity,
                speed, resuscitationEquipment, firstAidKits);
    }

}