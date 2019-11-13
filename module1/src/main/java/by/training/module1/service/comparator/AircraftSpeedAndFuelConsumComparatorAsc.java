package by.training.module1.service.comparator;

import by.training.module1.entity.Aircraft;

import java.util.Comparator;

public class AircraftSpeedAndFuelConsumComparatorAsc implements Comparator<Aircraft> {
    @Override
    public int compare(Aircraft o1, Aircraft o2) {
        int speedComp = Double.compare(o1.getSpeed(), o2.getSpeed());
        if (speedComp != 0) {
            return speedComp;
        } else {
            return Double.compare(o1.getFuelConsumption(), o2.getFuelConsumption());
        }
    }
}
