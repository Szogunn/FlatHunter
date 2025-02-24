package com.scrapper.entities;

public class Price {

    private double value;
    private String currency;

    public Price(double value) {
        this.value = value;
    }

    public Price(double value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public Price() {
    }

    public double getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public static Price add(Price price1, Price price2){
        if (price1 == null || price2 == null){
            return null;
        }

        return new Price(price1.getValue() + price1.getValue(), price1.getCurrency());
    }
}