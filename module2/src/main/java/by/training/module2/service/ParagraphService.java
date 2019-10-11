package by.training.module2.service;

import by.training.module2.model.TextComposite;
import by.training.module2.repository.TextRepository;

import java.util.Comparator;
import java.util.List;

public class ParagraphService {
    TextRepository<TextComposite> repository;

    public ParagraphService(TextRepository<TextComposite> repository) {
        this.repository = repository;
    }

    public TextComposite read(long id) {
        return repository.read(id);
    }

    public long create (TextComposite wordLeaf) {
        repository.create(wordLeaf);
        return wordLeaf.getId();
    }

    public void add (TextComposite paragraphComposite) {
        repository.add(paragraphComposite);
    }

    public void delete (TextComposite paragraphComposite) {
        repository.delete(paragraphComposite.getId());
    }

    public List<TextComposite> getAll () {
        return repository.getAll();
    }

    public void sort (List<TextComposite> sentences, Comparator<TextComposite> comparator) {
        sentences.sort(comparator);
    }
}


