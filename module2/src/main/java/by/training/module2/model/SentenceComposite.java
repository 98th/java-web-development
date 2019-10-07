package by.training.module2.model;


import java.util.LinkedList;
import java.util.List;

public class SentenceComposite implements TextComposite {
    private List<TextLeaf> words;
    private boolean isNewLine;


    public SentenceComposite(boolean isEndOfLine) {
        words = new LinkedList<>();
        this.isNewLine = isEndOfLine;
    }

    @Override
    public void addText(TextLeaf leaf) {
        words.add(leaf);
    }

    @Override
    public String getText() {
        StringBuilder str = new StringBuilder();
        if(isNewLine) {
            str.append("\t");
        }
        for (TextLeaf i : words) {
            str.append(i.getText());
            str.append(" ");
        }
        return str.toString();
    }

}
