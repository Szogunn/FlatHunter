package com.scrapper.parsers;

public class ApartmentSizeParser implements DataParser<Double>{

    @Override
    public boolean canParse(String input) {
        return input.endsWith("m²");
    }

    @Override
    public Double parse(String input) {
        return Double.parseDouble(input.replace("m²", "").trim());
    }
}
