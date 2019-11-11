package by.training.module3.model.state;

import by.training.module3.model.Truck;

public class LeavingTerminalState implements TruckState {
    @Override
    public void next(Truck truck) {

    }

    @Override
    public void prev(Truck truck) {
        truck.setTruckState(new ProcessingCargoState());
    }

    @Override
    public String getStatus() {
        return " is leaving the terminal ";
    }
}
