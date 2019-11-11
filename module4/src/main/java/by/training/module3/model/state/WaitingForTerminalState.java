package by.training.module3.model.state;

import by.training.module3.model.Truck;

public class WaitingForTerminalState implements TruckState {
    @Override
    public void next(Truck truck) {
        truck.setTruckState(new ProcessingCargoState());
    }

    @Override
    public void prev(Truck truck) {

    }

    @Override
    public String getStatus() {
        return " is waiting for the terminal to get free ";
    }
}
