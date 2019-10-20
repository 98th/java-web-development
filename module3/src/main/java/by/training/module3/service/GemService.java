package by.training.module3.service;

import by.training.module3.entity.Gem;
import by.training.module3.repository.Repository;

import java.util.List;
import java.util.Optional;

public class GemService implements Service<Gem> {
    private Repository<Gem> repository;

    public GemService(Repository<Gem> repository) {
        this.repository = repository;
    }

    public void add (Gem gem) {
        repository.add(gem);
    }

    public Optional<Gem> get (long id) {
        return repository.get(id);
    }

    public List<Gem> getAll() {
        return repository.getAll();
    }

}
