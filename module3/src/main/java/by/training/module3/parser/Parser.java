package by.training.module3.parser;

import java.util.List;

public interface Parser<T> {
    List<T> parse(String path) throws ParserException;
}
