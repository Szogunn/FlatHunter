package com.scrapper.utils;

import com.scrapper.entities.Point;

import java.util.Arrays;

public enum CityCenter {
    WARSZAWA(new Point(52.237049, 21.017532)),
    GDAŃSK(new Point(54.405633, 18.601487));

    private final Point centerPoint;

    CityCenter(Point point) {
        this.centerPoint = point;
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public static Point findCenterByCityName(String cityName){
        CityCenter[] values = values();
        return Arrays.stream(values).filter(city -> city.name().equalsIgnoreCase(cityName))
                .findAny()
                .map(CityCenter::getCenterPoint)
                .orElse(null);
    }
}
