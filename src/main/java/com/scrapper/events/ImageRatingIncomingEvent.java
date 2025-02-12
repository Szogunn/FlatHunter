package com.scrapper.events;

public class ImageRatingIncomingEvent {
    private String offerLink;
    private double rating;

    public ImageRatingIncomingEvent(String offerLink, double rating) {
        this.offerLink = offerLink;
        this.rating = rating;
    }

    public ImageRatingIncomingEvent() {
    }

    public void setOfferLink(String offerLink) {
        this.offerLink = offerLink;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getOfferLink() {
        return offerLink;
    }

    public double getRating() {
        return rating;
    }
}
