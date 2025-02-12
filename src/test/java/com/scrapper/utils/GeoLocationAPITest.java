package com.scrapper.utils;

import com.scrapper.entities.Address;
import com.scrapper.entities.Point;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

class GeoLocationAPITest {

    @Test
    void findPointByAddress() {
        RestTemplate restTemplate = new RestTemplate();
        GeoLocationAPI geoLocationAPI = new GeoLocationAPI(restTemplate);
        Address address = new Address();
        address.setStreet("Jana Kochanowskiego");
        address.setCityName("Warszawa");
        Point s = geoLocationAPI.findPointByAddress(address);
        System.out.println(s);
    }
}