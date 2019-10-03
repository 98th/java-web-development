package by.training.module1.service;

import by.training.module1.entity.Aircraft;
import by.training.module1.repository.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AircraftService {
    private Repository<Aircraft> repository;

    public AircraftService(Repository<Aircraft> repository) {
        this.repository = repository;
    }

    public void add (Aircraft aircraft) {
        repository.add(aircraft);
    }

    public List<Aircraft> getAll () {
        return repository.getAll();
    }

    public double calculateCargoCapacity () {
        double result = 0;
        for (Aircraft i : repository.getAll()) {
            result += i.getLoadCapacity();
        }
        return result;
    }

    public int calculatePassengers () {
        int result = 0;
        for (Aircraft i : repository.getAll()) {
            result += i.getPassengers();
        }
        return result;
    }

    public List<Aircraft> sort (Comparator<Aircraft> comparator) {
        List<Aircraft> output = new ArrayList<>(repository.getAll());
        output.sort(comparator);
        return output;
    }
}