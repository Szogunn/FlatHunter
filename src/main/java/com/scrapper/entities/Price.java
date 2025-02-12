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
}