package by.training.module3.model.state;

import by.training.module3.model.Truck;

public class ProcessingCargoState implements TruckState {
    @Override
    public void next(Truck truck) {
        truck.setTruckState(new LeavingTerminalState());
    }

    @Override
    public void prev(Truck truck) {
        truck.setTruckState(new WaitingForTerminalState());
    }

    @Override
    public String getStatus() {
        return " took the terminal ";
    }
}
