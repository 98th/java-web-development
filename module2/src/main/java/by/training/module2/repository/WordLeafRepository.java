package by.training.module2.repository;

import by.training.module2.model.WordLeaf;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WordLeafRepository implements Repository<WordLeaf> {
    private List<WordLeaf> words;

    public WordLeafRepository() {
        words = new LinkedList<>();
    }

    @Override
    public void add(WordLeaf wordLeaf) {
        words.add(wordLeaf);
    }

    @Override
    public void delete(WordLeaf wordLeaf) {
        words.remove(wordLeaf);
    }

    @Override
    public List<WordLeaf> find(Specification<WordLeaf> spec) {
        List<WordLeaf> output = new ArrayList<>();
        for (WordLeaf i : words) {
            if (spec.match(i)) {
                output.add(i);
            }
        }
        return output;
    }

    @Override
    public List<WordLeaf> getAll() {
        return new ArrayList<>(words);
    }
}
