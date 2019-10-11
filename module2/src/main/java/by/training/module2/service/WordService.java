package by.training.module2.service;

import by.training.module2.model.TextLeaf;
import by.training.module2.repository.TextRepository;


import java.util.List;

public class WordService {
    private TextRepository<TextLeaf> repository;

    public WordService(TextRepository<TextLeaf> repository) {
        this.repository = repository;
    }

    public TextLeaf read(long id) {
        return repository.read(id);
    }

    public long create (TextLeaf word) {
        repository.create(word);
        return word.getId();
    }

    public void add (TextLeaf word) {
        repository.add(word);
    }

    public void delete (TextLeaf word) {
        repository.delete(word.getId());
    }

    public List<TextLeaf> getAll () {
        return repository.getAll();
    }

}
