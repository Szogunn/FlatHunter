package com.scrapper.searchers;

import org.junit.jupiter.api.Test;

class UrlBuilderTest {


    @Test
    void buildUrl() {
        OfferSearchCriteria criteria = new OfferSearchCriteria.Builder(true)
                .cityLocation(City.GDANSK)
                .maxPrice(800000)
                .areaMin(50)
                .areaMax(80)
                .roomsQuantity(new int[]{2,3})
                .build();

        UrlBuilder builder = new OtoDomUrlBuilder();
        String url = builder.buildUrl(criteria);
        System.out.println(url);
    }
}