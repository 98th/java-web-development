package by.training.module2.parser;

public interface ParserChain<T> {
    T parse (String text);
    ParserChain<T> linkWith (ParserChain<T> next);
}
