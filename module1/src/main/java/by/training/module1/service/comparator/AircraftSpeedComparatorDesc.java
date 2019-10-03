package by.training.module1.service.comparator;

import by.training.module1.entity.Aircraft;

import java.util.Comparator;

public class AircraftSpeedComparatorDesc implements Comparator <Aircraft> {
    @Override
    public int compare(Aircraft o1, Aircraft o2) {
        return Double.compare(o1.getSpeed(), o2.getSpeed());
    }
}

