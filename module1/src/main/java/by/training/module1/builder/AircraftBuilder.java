package by.training.module1.builder;

import by.training.module1.entity.Aircraft;

import java.util.Map;

public interface AircraftBuilder {
    Aircraft build(Map<String, String> aircraft);
}
