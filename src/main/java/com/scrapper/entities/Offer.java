package com.scrapper.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document
public class Offer {

    @Id
    private String link;
    private boolean isOnSell;
    private Map<String, String> attributes;
    private String description;
    private Address address;
    private Price price;
    private double pricePerSquare;
    private double apartmentSize;
    private int numberOfRooms;
    private double distanceFromCenter;
    private double imagesRating;
    private double offerScore;
    private int version;

    public Offer(String link) {
        this.link = link;
    }

    public Offer() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(String key, String value) {
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
        }

        this.attributes.put(key, value);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getApartmentSize() {
        return apartmentSize;
    }

    public void setApartmentSize(double apartmentSize) {
        this.apartmentSize = apartmentSize;

        if (pricePerSquare == 0 && price != null && apartmentSize != 0){
            this.pricePerSquare = price.getValue() / apartmentSize;
        }
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Price getPrice() {
        if (this.price != null) {
            return price;
        }

        if (this.pricePerSquare != 0 && apartmentSize != 0){
            return new Price(this.pricePerSquare * apartmentSize);
        }

        return null;
    }

    public void setPrice(Price price) {
        this.price = price;

        if (pricePerSquare == 0 && apartmentSize != 0){
            this.pricePerSquare = price.getValue() / apartmentSize;
        }
    }

    public double getPricePerSquare() {
        if (pricePerSquare != 0){
            return pricePerSquare;
        }

        if (this.price != null && this.apartmentSize != 0){
            return price.getValue() / apartmentSize;
        }

        return 0;
    }

    public void setPricePerSquare(double pricePerSquare) {
        this.pricePerSquare = pricePerSquare;
    }

    public double getDistanceFromCenter() {
        return distanceFromCenter;
    }

    public void setDistanceFromCenter(double distanceFromCenter) {
        this.distanceFromCenter = distanceFromCenter;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public double getImagesRating() {
        return imagesRating;
    }

    public void setImagesRating(double imagesRating) {
        this.imagesRating = imagesRating;
    }

    public boolean isOnSell() {
        return isOnSell;
    }

    public void setOnSell(boolean onSell) {
        isOnSell = onSell;
    }

    public double getOfferScore() {
        return offerScore;
    }

    public void setOfferScore(double offerScore) {
        this.offerScore = offerScore;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
