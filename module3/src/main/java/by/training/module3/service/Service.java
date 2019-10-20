package by.training.module3.service;

import java.util.List;
import java.util.Optional;

public interface Service<T> {
     void add (T entity);
     Optional<T> get (long id);
     List<T> getAll();
}
