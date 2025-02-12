package com.scrapper.utils;

import com.scrapper.entities.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityCenterTest {

    @Test
    void findCenterByCityName() {
        Point point = CityCenter.findCenterByCityName("Gdansk");
        System.out.println(point.getLat());
    }
}