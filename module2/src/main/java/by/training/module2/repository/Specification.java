package by.training.module2.repository;

import by.training.module2.model.TextLeaf;

public interface Specification<T extends TextLeaf> {
    boolean  match(T entity);

    default Specification<T> and(Specification<T> spec) {
        return entity -> this.match(entity)
                && spec.match(entity);
    }
}
