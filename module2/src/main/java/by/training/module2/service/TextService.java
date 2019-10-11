package by.training.module2.service;

import by.training.module2.model.SentenceComposite;
import by.training.module2.model.TextLeaf;
import by.training.module2.repository.TextRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class TextService<T extends TextLeaf> {
    TextRepository<T> repository;

    public TextService(TextRepository<T> repository) {
        this.repository = repository;
    }


    public T read(long id) {
        return repository.read(id);
    }

    public long create (T entity) {
        repository.create(entity);
        return entity.getId();
    }

    public void add (T entity) {
        repository.add(entity);
    }

    public void delete (T entity) {
        repository.delete(entity.getId());
    }

    public List<T> getAll () {
        return repository.getAll();
    }

    public List<T> sort (Comparator<T> comparator) {
        List<T> output = new ArrayList<>(repository.getAll());
        output.sort(comparator);
        return output;
    }
}
