package by.training.module2.model;


import java.util.List;

public interface TextComposite extends TextLeaf {
    void addText(TextLeaf leaf);
    List<TextLeaf> getLeaves();
}
