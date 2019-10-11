package by.training.module2.model;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ParagraphComposite implements TextComposite {
    private List<TextLeaf> sentences;
    private long id;
    private static final AtomicLong nextId = new AtomicLong(1);

    public ParagraphComposite() {
        sentences = new LinkedList<>();
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
        return sentences;
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
