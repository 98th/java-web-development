package by.training.module1.repository;

import java.util.List;

public interface Repository <T> {
    void add(T entity);
    List<T> find (Specification<T> spec);
    List<T> getAll();
}
