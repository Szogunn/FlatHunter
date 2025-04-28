package com.scrapper.searchers;

public class OfferSearchCriteria {

    private boolean isOnSell;
    private int[] roomsQuantity;
    private City cityLocation;
    private double maxPrice;
    private double areaMin;
    private double areaMax;

    public OfferSearchCriteria(Builder builder){
        this.isOnSell = builder.isOnSell;
        this.roomsQuantity = builder.roomsNumber;
        this.cityLocation = builder.cityLocation;
        this.maxPrice = builder.maxPrice;
        this.areaMin = builder.areaMin;
        this.areaMax = builder.areaMax;
    }

    public int[] getRoomsQuantity() {
        return roomsQuantity;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public double getAreaMin() {
        return areaMin;
    }

    public City getCityLocation() {
        return cityLocation;
    }

    public double getAreaMax() {
        return areaMax;
    }

    public boolean isOnSell() {
        return isOnSell;
    }

    public static class Builder{
        private final boolean isOnSell;
        private int[] roomsNumber;
        private City cityLocation;
        private double maxPrice;
        private double areaMin;
        private double areaMax;

        public Builder(boolean isOnSell){
            this.isOnSell = isOnSell;
        }

        public Builder roomsQuantity(int[] roomsQuantity){
            this.roomsNumber = roomsQuantity;
            return this;
        }

        public Builder cityLocation(City cityLocation){
            this.cityLocation = cityLocation;
            return this;
        }

        public Builder maxPrice(double maxPrice){
            this.maxPrice = maxPrice;
            return this;
        }

        public Builder areaMin(double areaMin){
            this.areaMin = areaMin;
            return this;
        }

        public Builder areaMax(double areaMax){
            this.areaMax = areaMax;
            return this;
        }

        public OfferSearchCriteria build(){
            return new OfferSearchCriteria(this);
        }
    }
}