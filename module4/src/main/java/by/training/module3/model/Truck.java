package by.training.module3.model;

import by.training.module3.model.state.TruckState;
import by.training.module3.model.state.WaitingForTerminalState;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.*;

import static by.training.module3.model.OperationType.LOADING;
import static by.training.module3.model.OperationType.UNLOADING;

public class Truck extends Thread {
    private static final Logger log = LogManager.getLogger(LogisticBase.class);
    private List<Item> items;
    private  int capacity;
    private boolean isCargoPerishable;
    private boolean isCargoProcessed;
    private LogisticBase logisticBase;
    private long id;
    private OperationType operationType;
    private TruckState state = new WaitingForTerminalState();

    public Truck(long id, int capacity,
                 boolean isCargoPerishable, OperationType operationType) {
        this.id = id;
        this.capacity = capacity;
        this.isCargoPerishable = isCargoPerishable;
        this.operationType = operationType;
        this.isCargoProcessed = false;
        items = new ArrayList<>(capacity);
        if (isCargoPerishable) {
            this.setPriority(6);
        }
    }

    @Override
    public void run() {
        Terminal currentTerminal  = null;
        log.info("the truck " + id + state.getStatus());
        try {
            currentTerminal = logisticBase.getTerminal();
            if (currentTerminal != null) {
                nextTruckState();
                log.info("the truck " + id + state.getStatus()  + currentTerminal.getId());
                if (operationType.equals(LOADING)) {
                    loadTruck();
                } else if (operationType.equals(UNLOADING)) {
                    unloadTruck();
                }
            }
        } catch (LogisticBaseException e) {
            log.error(e.getMessage());
        } finally {
            if (currentTerminal != null) {
                nextTruckState();
                log.info("the truck " + id + this.state.getStatus() + currentTerminal.getId() + " with " + items.size() +" items");
                logisticBase.releaseTerminal(currentTerminal);
            }
        }

    }

    private void loadTruck() throws LogisticBaseException {
        int num = capacity - items.size();
        log.info("the ship " + id + " has loaded " + num +" items from the terminal " + logisticBase.getId());
        List<Item> unloadedItems = logisticBase.unloadItems( capacity - items.size());
        if (unloadedItems == null) {
            return;
        }
        this.addItems(unloadedItems);
        isCargoProcessed = true;
    }

    private void unloadTruck () throws LogisticBaseException {
        List<Item> output = new ArrayList<>(items);
        log.info("the ship " + id + " has unloaded " + items.size() +" items to the terminal " + logisticBase.getId());
        logisticBase.loadItem(output);
        this.items.clear();
        isCargoProcessed = true;
    }

    public void addItems(List<Item> items) {
        this.items.addAll(items);
    }

    private void setTruckState(TruckState state) {
        this.state = state;
    }

    private void nextTruckState() {
        state.next(this);
    }

    public void setLogisticBase(LogisticBase logisticBase) {
        this.logisticBase = logisticBase;
    }

}
