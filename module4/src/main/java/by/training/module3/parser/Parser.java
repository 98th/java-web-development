package by.training.module3.parser;

public interface Parser<T> {
    T parse(String path) throws ParserException;
}