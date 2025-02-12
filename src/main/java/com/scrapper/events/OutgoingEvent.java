package com.scrapper.events;

import java.util.List;

public class OutgoingEvent {

    private final String offerLink;
    private final List<String> imagesLinks;

    public OutgoingEvent(String offerLink, List<String> imagesLinks) {
        this.offerLink = offerLink;
        this.imagesLinks = imagesLinks;
    }

    public String getOfferLink() {
        return offerLink;
    }

    public List<String> getImagesLinks() {
        return imagesLinks;
    }
}
