package com.scrapper.entities;

import java.util.Objects;

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

        return new Price(price1.getValue() + price2.getValue(), price1.getCurrency());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Price price = (Price) object;
        return Double.compare(value, price.value) == 0 && Objects.equals(currency, price.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }

    @Override
    public String toString() {
        return "Price{" +
                "value=" + value +
                ", currency='" + currency + '\'' +
                '}';
    }
}