package by.training.module2.repository;

import by.training.module2.model.TextLeaf;

import java.util.List;

public interface Repository<T extends TextLeaf> {
    long create(T entity);
    void add(T entity);
    T read (long id);
    void delete(long id);
    List<T> find(Specification<T> spec);
    List<T> getAll();
}
