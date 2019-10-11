package by.training.module2.repository;

import by.training.module2.model.TextLeaf;

import java.util.*;
import java.util.stream.Collectors;

public class TextRepository<T  extends TextLeaf> implements Repository<T> {
    protected Map<Long, T> leaves;

    public TextRepository() {
        leaves = new HashMap<>();
    }

    @Override
    public long create(T entity) {
        leaves.put(entity.getId(), entity);
        return entity.getId();
    }

    @Override
    public void add(T entity) {
        leaves.put(entity.getId(), entity);
    }

    @Override
    public T read(long id) {
        return leaves.get(id);
    }

    @Override
    public void delete(long id) {
        if (leaves.containsKey(id)) {
            leaves.remove(id);
        }
    }

    @Override
    public List<T> find(Specification<T> spec) {
        return leaves.values().stream().filter(spec::match).collect(Collectors.toList());
    }

    @Override
    public List<T> getAll() {
        return new LinkedList<T>(leaves.values());
    }
}
