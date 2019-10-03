package by.training.module1.repository;

import java.util.ArrayList;
import java.util.List;

public class AircraftRepository<Aircraft> implements Repository<Aircraft> {
    private List<Aircraft> airline;

    public AircraftRepository() {
        airline = new ArrayList<>();
    }

    @Override
    public void add (Aircraft aircraft) {
        airline.add(aircraft);
    }

    @Override
    public List<Aircraft> find(Specification<Aircraft> spec) {
        List<Aircraft> output = new ArrayList<>();
        for (Aircraft i : airline) {
            if (spec.match(i)) {
                output.add(i);
            }
        }
        return output;
    }

    @Override
    public List<Aircraft> getAll () {
        return new ArrayList<>(airline);
    }

}