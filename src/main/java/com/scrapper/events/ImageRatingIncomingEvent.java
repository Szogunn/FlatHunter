package com.scrapper.events;

public class ImageRatingIncomingEvent {
    private String offerLink;
    private int offerVersion;
    private double rating;

    public ImageRatingIncomingEvent(String offerLink, int offerVersion, double rating) {
        this.offerLink = offerLink;
        this.offerVersion = offerVersion;
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

    public int getOfferVersion() {
        return this.offerVersion;
    }
}
