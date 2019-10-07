package by.training.module2.service;

import by.training.module2.model.WordLeaf;
import by.training.module2.repository.WordLeafRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WordLeafService implements TextService<WordLeaf> {
    private WordLeafRepository repository;

    public WordLeafService(WordLeafRepository repository) {
        this.repository = repository;
    }

    public void add (WordLeaf wordLeaf) {
        repository.add(wordLeaf);
    }

    public void delete (WordLeaf wordLeaf) {
        repository.delete(wordLeaf);
    }

    public List<WordLeaf> getAll () {
        return repository.getAll();
    }

    public List<WordLeaf> sort (Comparator<WordLeaf> comparator) {
        List<WordLeaf> output = new ArrayList<>(repository.getAll());
        output.sort(comparator);
        return output;
    }
}
