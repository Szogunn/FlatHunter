package com.scrapper.services;

import com.scrapper.entities.Address;
import com.scrapper.entities.Point;
import com.scrapper.utils.CityCenter;
import com.scrapper.utils.GeoLocationAPI;
import org.springframework.stereotype.Service;

@Service
public class WebService {

    private final GeoLocationAPI geoLocationAPI;

    public WebService(GeoLocationAPI geoLocationAPI) {
        this.geoLocationAPI = geoLocationAPI;
    }

    public double findDistanceFromCenter(Address address){
        Point addressPoint = geoLocationAPI.findPointByAddress(address);
        if (addressPoint == null){
            return 0;
        }

        Point cityCenter = CityCenter.findCenterByCityName(address.getCityName());
        return cityCenter != null ? calculateDistance(addressPoint.getLat(), addressPoint.getLon(), cityCenter.getLat(), cityCenter.getLon()) : 0.0;
    }

    double calculateDistance(double startLat, double startLong, double endLat, double endLong) {

        double dLat = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);

        double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return 6371 * c;
    }

    double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
