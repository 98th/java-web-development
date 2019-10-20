package by.training.module3.repository;

import by.training.module3.entity.Gem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GemRepository implements Repository<Gem> {
    private List<Gem> gems;

    public GemRepository() {
        gems = new ArrayList<>();
    }

    @Override
    public void add(Gem gem) {
        gems.add(gem);
    }

    @Override
    public Optional<Gem> get(long id) {
        return gems.stream().filter(i -> i.getId() == id).findFirst();
    }

    @Override
    public List<Gem> getAll() {
        return new ArrayList<>(gems);
    }
}
