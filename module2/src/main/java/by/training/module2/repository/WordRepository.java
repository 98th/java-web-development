package by.training.module2.repository;

import by.training.module2.model.TextLeaf;


import java.util.concurrent.atomic.AtomicLong;

public class WordRepository extends TextRepository<TextLeaf> {
    private AtomicLong nextId = new AtomicLong(1);

    @Override
    public void add (TextLeaf entity) {
        this.leaves.put(nextId.getAndIncrement(), entity);
    }

    @Override
    public long create(TextLeaf entity) {
        this.leaves.put(nextId.getAndIncrement(), entity);
        return nextId.get();
    }
}
