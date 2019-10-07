package by.training.module2.model;

import java.util.List;
import java.util.stream.Collectors;

public class WholeTextComposite implements  TextComposite {
    private List<TextLeaf> paragraphs;

    public WholeTextComposite(List<TextLeaf> paragraphs) {
        this.paragraphs = paragraphs;
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
