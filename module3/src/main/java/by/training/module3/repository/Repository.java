package by.training.module3.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    void add(T gem);
    Optional<T> get(long id);
    List<T> getAll();
}
