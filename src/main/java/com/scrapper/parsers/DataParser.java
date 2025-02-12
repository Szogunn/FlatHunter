package com.scrapper.parsers;

public interface DataParser<T> {
    boolean canParse(String input);
    T parse(String input);
}
