package by.training.module2.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class WholeTextComposite implements  TextComposite {
    private List<TextLeaf> paragraphs;
    private long id;

    public WholeTextComposite (){
        paragraphs = new LinkedList<>();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public List<TextLeaf> getLeaves() {
        return paragraphs;
    }

    @Override
    public void addText(TextLeaf leaf) {
        paragraphs.add(leaf);
    }

    @Override
    public String getText() {
        return paragraphs.stream().map(i -> i.getText()).collect(Collectors.joining("\n"));
    }
}
