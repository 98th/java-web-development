package by.training.module2.service;

import java.util.List;

public interface TextService <T> {
    void add (T entity);
    void delete (T entity);
    List<T> getAll();
}
