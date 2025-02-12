package com.scrapper.searchers;

import org.junit.jupiter.api.Test;

class UrlBuilderTest {


    @Test
    void buildUrl() {
        OfferSearchCriteria criteria = new OfferSearchCriteria();
        criteria.setCityLocation(City.GDANSK);
//        criteria.setOnSell(true);
        criteria.setMaxPrice(800000D);
        criteria.setAreaMin(25d);
        criteria.setAreaMax(60d);
        int[] roomNumbers = {3,4};
        criteria.setRoomsNumber(roomNumbers);

        UrlBuilder builder = new OtoDomUrlBuilder();
        String url = builder.buildUrl(criteria);
        System.out.println(url);
    }
}