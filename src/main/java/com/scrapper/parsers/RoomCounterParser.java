package com.scrapper.parsers;

public class RoomCounterParser implements DataParser<Integer>{

    @Override
    public boolean canParse(String input) {
        return input.contains("pok");
    }

    @Override
    public Integer parse(String input) {
        return Integer.parseInt(input.replaceAll("[^0-9]", ""));
    }
}
