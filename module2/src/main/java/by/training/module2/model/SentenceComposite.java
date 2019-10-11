package by.training.module2.model;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class SentenceComposite implements TextComposite {
    private List<TextLeaf> words;
    private boolean isNewLine;
    private long id;
    private static final AtomicLong nextId = new AtomicLong(1);

    public SentenceComposite (boolean isNewLine) {
        this.isNewLine = isNewLine;
        words = new LinkedList<>();
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
        return words;
    }

    @Override
    public void addText(TextLeaf leaf) {
        words.add(leaf);
    }

    @Override
    public String getText() {
        StringBuilder str = new StringBuilder();
        for (TextLeaf i : words) {
            if(isNewLine) {
                str.append("\n");
            }
            str.append(i.getText());
            str.append(" ");

        }
        return str.toString();
    }
}
