package by.training.module1.repository;

import by.training.module1.entity.Aircraft;

public class SpecificationByFuelConsumption implements Specification<Aircraft> {
    private double min;
    private double max;

    public SpecificationByFuelConsumption(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean match(Aircraft aircraft) {
        return min <= aircraft.getFuelConsumption() && aircraft.getFuelConsumption() <= max;
    }
}
