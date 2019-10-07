package by.training.module2.service;

import by.training.module2.model.WordLeaf;

import java.util.Comparator;

public class WordLeafLengthComparator implements Comparator<WordLeaf> {
    private boolean isAscendingOrder;

    @Override
    public int compare(WordLeaf o1, WordLeaf o2) {
        return isAscendingOrder ? Integer.compare(o1.getText().length(), o2.getText().length()) :
                                  Integer.compare(o2.getText().length(), o1.getText().length());
    }
}
