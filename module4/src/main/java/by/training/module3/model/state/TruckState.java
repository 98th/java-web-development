package by.training.module3.model.state;

import by.training.module3.model.Truck;

public interface TruckState {
    void next(Truck truck);
    void prev(Truck truck);
    String getStatus();
}
