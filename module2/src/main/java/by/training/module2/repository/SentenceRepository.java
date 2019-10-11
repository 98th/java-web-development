package by.training.module2.repository;

import by.training.module2.model.SentenceComposite;
import by.training.module2.model.TextComposite;

import java.util.concurrent.atomic.AtomicLong;

public class SentenceRepository extends TextRepository<TextComposite> {
    private AtomicLong nextId = new AtomicLong(1);

    @Override
    public void add (TextComposite entity) {
        this.leaves.put(nextId.getAndIncrement(), entity);
    }

    @Override
    public long create(TextComposite entity) {
        this.leaves.put(nextId.getAndIncrement(), entity);
        return nextId.get();
    }
}
