package com.scrapper.searchers;

public interface UrlBuilder {
    String buildUrl(OfferSearchCriteria criteria);
    String getPageSuffix();
}
