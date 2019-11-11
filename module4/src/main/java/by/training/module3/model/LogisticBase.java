package by.training.module3.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticBase {
    private static Lock lock = new ReentrantLock();
    private static LogisticBase logisticBase;
    private final int TERMINAL_NUMBER = 2;

    private long id;
    private int capacity;
    private List<Item> items;
    private Queue<Terminal> terminals;
    private Semaphore semaphore = new Semaphore(TERMINAL_NUMBER);

    private LogisticBase(long id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        terminals = new LinkedList<>();
        items = new ArrayList<>(capacity);
    }

    public void addTerminals(Queue<Terminal> terminals) {
        this.terminals.addAll(terminals);
    }

    public void addItems(List<Item> items) {
        this.items.addAll(items);
    }

    public Terminal getTerminal() throws LogisticBaseException {
        try{
            semaphore.acquire();
            lock.lock();
            Terminal res = terminals.poll();
            lock.unlock();
            return res;
        } catch (InterruptedException e) {
            throw new LogisticBaseException(e);
        }

    }

    public void releaseTerminal(Terminal res) {
        lock.lock();
        try {
            terminals.add(res);
            semaphore.release();
        } finally {
            lock.unlock();
        }

    }

    public void loadItem (List<Item> items) throws LogisticBaseException {
         lock.lock();
        try {
            this.items.addAll(items);
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
                  lock.unlock();
            throw new LogisticBaseException(e);
        }
           lock.unlock();
    }

    public List<Item> unloadItems (int count) throws LogisticBaseException {
        List<Item> output;
        if (count > items.size()) {
            throw new LogisticBaseException("there are not sou much items on the base");
        }
        lock.lock();
        try {
            output = new ArrayList<>(items.subList(0, count));
            items.removeAll(output);
        } finally {
            lock.unlock();
        }
        return output;
    }


    public static LogisticBase getInstance(long id, int capacity) {
        if (logisticBase == null) {
            lock.lock();
            if (logisticBase == null) {
                logisticBase = new LogisticBase(id, capacity);
            }
            lock.unlock();
        }
        return logisticBase;
    }

    public long getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public Queue<Terminal> getTerminals() {
        return terminals;
    }
}
