package com.scrapper.parsers;

import com.scrapper.entities.Price;

public class PriceParser implements DataParser<Price> {
    @Override
    public boolean canParse(String input) {
        return input.endsWith("zł") || input.endsWith("zł/mc");
    }

    @Override
    public Price parse(String input) {
        double value = Double.parseDouble(input.replaceAll("[^0-9]", "").trim());
        String currency = input.replaceAll("[0-9]", "").trim();
        return new Price(value, currency);
    }
}
