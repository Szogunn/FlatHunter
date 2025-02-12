package com.scrapper.searchers;

public class OfferSearchCriteria {

    private boolean isOnSell;
    private int[] roomsNumber;
    private City cityLocation;
    private double maxPrice;
    private double areaMin;
    private double areaMax;

    // Gettery i settery

    public int[] getRoomsNumber() {
        return roomsNumber;
    }

    public void setRoomsNumber(int[] roomsNumber) {
        this.roomsNumber = roomsNumber;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getAreaMin() {
        return areaMin;
    }

    public void setAreaMin(Double areaMin) {
        this.areaMin = areaMin;
    }

    public City getCityLocation() {
        return cityLocation;
    }

    public void setCityLocation(City cityLocation) {
        this.cityLocation = cityLocation;
    }

    public double getAreaMax() {
        return areaMax;
    }

    public void setAreaMax(double areaMax) {
        this.areaMax = areaMax;
    }

    public boolean isOnSell() {
        return isOnSell;
    }

    public void setOnSell(boolean onSell) {
        isOnSell = onSell;
    }
}