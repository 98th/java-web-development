package by.training.module2.service;

import by.training.module2.model.TextComposite;

import java.util.Comparator;

public class WordLeafSizeComparator implements Comparator<TextComposite> {
    private boolean isAscendingOrder;

    public WordLeafSizeComparator(boolean isAscendingOrder) {
        this.isAscendingOrder = isAscendingOrder;
    }

    @Override
    public int compare(TextComposite o1, TextComposite o2) {
        return isAscendingOrder ? Integer.compare(o1.getLeaves().size(), o2.getLeaves().size()) :
                                  Integer.compare(o2.getLeaves().size(), o1.getLeaves().size());
    }
}
