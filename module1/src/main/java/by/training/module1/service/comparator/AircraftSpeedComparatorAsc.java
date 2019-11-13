package by.training.module1.service.comparator;

import by.training.module1.entity.Aircraft;

import java.util.Comparator;

public class AircraftSpeedComparatorAsc implements Comparator<Aircraft> {
    @Override
    public int compare(Aircraft o1, Aircraft o2) {
        return Double.compare(o2.getSpeed(), o1.getSpeed());
    }
}