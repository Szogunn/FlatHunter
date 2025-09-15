package com.scrapper.searchers;

public interface UrlBuilder {
    String buildUrl(SearchCriteria criteria);
    String getPageSuffix();
}
