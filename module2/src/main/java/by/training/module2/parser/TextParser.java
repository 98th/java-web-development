package by.training.module2.parser;

import by.training.module2.model.TextLeaf;

import java.util.List;

public abstract class TextParser implements ParserChain<TextLeaf> {
    private TextParser next;

    @Override
    public ParserChain<TextLeaf> linkWith (ParserChain<TextLeaf> next){
        ((TextParser) next).next = this;
        return next;
    }

    protected List<? extends TextLeaf> nextParse (String line) {
        if (next != null) {
            return next.parse(line);
        } else {
            return null;
        }
    }
/*

    protected TextLeaf nextParse (String line) {
        if (next != null) {
            return next.parse(line);
        } else {
            return null;
        }
    }*/
}
