package by.training.module2.model;


import java.util.LinkedList;
import java.util.List;

public class ParagraphComposite implements TextComposite {
    private List<TextLeaf> sentences;

    public ParagraphComposite() {
        sentences = new LinkedList<>();
    }

    @Override
    public void addText(TextLeaf leaf) {
        this.sentences.add(leaf);
    }

    @Override
    public String getText() {
        StringBuilder str = new StringBuilder();
        for (TextLeaf i : sentences) {
            str.append(i.getText());
       //     str.append(" ");
        }
        return str.toString();
    }

}
