package com.scrapper.events;

import java.util.List;

public class OutgoingEvent {

    private final String offerLink;
    private final int offerVersion;
    private final List<String> imagesLinks;

    public OutgoingEvent(String offerLink, int offerVersion, List<String> imagesLinks) {
        this.offerLink = offerLink;
        this.offerVersion = offerVersion;
        this.imagesLinks = imagesLinks;
    }

    public String getOfferLink() {
        return offerLink;
    }

    public List<String> getImagesLinks() {
        return imagesLinks;
    }

    public int getOfferVersion() {
        return offerVersion;
    }
}
