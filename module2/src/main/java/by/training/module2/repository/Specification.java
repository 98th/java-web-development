package by.training.module2.repository;

public interface Specification<T> {
    boolean  match(T entity);

    default Specification<T> and(Specification<T> spec) {
        return entity -> this.match(entity)
                && spec.match(entity);
    }
}
