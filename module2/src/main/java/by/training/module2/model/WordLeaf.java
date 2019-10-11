package by.training.module2.model;

import java.util.concurrent.atomic.AtomicLong;

public class WordLeaf implements TextLeaf {
    private String word;
    private String ending;
    private String beginning;
    private long id;
    private static final AtomicLong nextId = new AtomicLong(1);

    public WordLeaf(String beginning, String word, String ending) {
        this.beginning = beginning;
        this.word = word;
        this.ending = ending;
        id = nextId.getAndIncrement();

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
    public String getText() {
        StringBuilder sb = new StringBuilder();
        if (beginning != null) {
            sb.append(beginning);
        }
        if (word != null) {
            sb.append(word);
        }
        if (ending != null) {
            sb.append(ending);
        }
        return sb.toString();
    }


}
