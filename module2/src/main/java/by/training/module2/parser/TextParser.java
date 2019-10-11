package by.training.module2.parser;

import by.training.module2.model.TextLeaf;

public abstract class TextParser implements ParserChain<TextLeaf> {
    private TextParser next;

    @Override
    public ParserChain<TextLeaf> linkWith (ParserChain<TextLeaf> next){
        ((TextParser) next).next = this;
        return next;
    }

    protected TextLeaf nextParse (String line, long ... id) {
        if (next != null) {
            return next.parse(line);
        } else {
            return null;
        }
    }

}
