package by.training.module2.service;

import by.training.module2.model.SentenceComposite;
import by.training.module2.repository.TextRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SentenceService {
    TextRepository<SentenceComposite> repository;

    public SentenceService(TextRepository<SentenceComposite> repository) {
        this.repository = repository;
    }


    public SentenceComposite read(long id) {
        return repository.read(id);
    }

    public long create (SentenceComposite wordLeaf) {
        repository.create(wordLeaf);
        return wordLeaf.getId();
    }

    public void add (SentenceComposite sentenceComposite) {
        repository.add(sentenceComposite);
    }

    public void delete (SentenceComposite sentenceComposite) {
        repository.delete(sentenceComposite.getId());
    }

    public List<SentenceComposite> getAll () {
        return repository.getAll();
    }

    public List<SentenceComposite> sort (Comparator<SentenceComposite> comparator) {
        List<SentenceComposite> output = new ArrayList<>(repository.getAll());
        output.sort(comparator);
        return output;
    }
}
