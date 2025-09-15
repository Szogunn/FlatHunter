package com.scrapper.searchers;

import com.scrapper.entities.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    void buildLandUrl() {
        Point [] points = { new Point(52.26356045953271, 21.151524985447708), new Point(52.17025621983384, 20.894766170596437) };

        LandCriteria criteria = new LandCriteria.Builder()
                .maxPrice(350000)
                .daysSinceCreated(7)
                .areaMin(700)
                .plotType(List.of("BUILDING"))
                .mapBounds(points)
                .build();

        UrlBuilder builder = new OtoDomUrlBuilder();
        String url = builder.buildUrl(criteria);
        String expected = "https://www.otodom.pl/pl/wyniki/sprzedaz/dzialka/cala-polska?limit=36&daysSinceCreated=7&priceMax=350000&areaMin=700&plotType=%5BBUILDING%5D&by=DEFAULT&direction=DESC&viewType=map&mapBounds=21.151524985447708%2C52.26356045953271%2C20.894766170596437%2C52.17025621983384";
        Assertions.assertEquals(expected, url);
    }
}