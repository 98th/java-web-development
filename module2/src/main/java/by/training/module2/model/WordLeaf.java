package by.training.module2.model;

public class WordLeaf implements TextLeaf {
    private String word;
    private String ending;

    public WordLeaf(String word, String ending) {
        this.word = word;
        this.ending = ending;
    }

    @Override
    public String getText() {
        StringBuilder sb = new StringBuilder();
        if (word != null) {
            sb.append(word);
        }
        if (ending != null) {
            sb.append(ending);
        }
        return sb.toString();
    }
}
