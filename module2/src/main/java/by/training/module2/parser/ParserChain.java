package by.training.module2.parser;

import java.util.List;

public interface ParserChain<T> {
    List<? extends T> parse (String text);
   // T parse (String text);
    ParserChain<T> linkWith (ParserChain<T> next);
}
