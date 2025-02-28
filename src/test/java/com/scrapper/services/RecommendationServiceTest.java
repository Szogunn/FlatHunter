package com.scrapper.services;

import com.scrapper.entities.Price;
import com.scrapper.repositories.OfferRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RecommendationServiceTest {

    OfferRepository offerRepository = Mockito.mock(OfferRepository.class);
    RecommendationService service = new RecommendationService(offerRepository);

    @Test
    void normalizeRentalPriceShouldReturnZero() {
        double result = service.normalizeRentalPrice(new Price(2001, "zł"));
        Assertions.assertEquals(0, result);
    }

    @Test
    void normalizeRentalPriceShouldReturn1() {
        service.setPriceRentalMax(2000);
        double result = service.normalizeRentalPrice(new Price(999, "zł"));
        Assertions.assertEquals(1, result);
    }

    @Test
    void normalizeSellPriceShouldReturnZero() {
        double result = service.normalizeSellPrice(new Price(2001, "zł"));
        Assertions.assertEquals(0, result);
    }

    @Test
    void normalizeSellPriceShouldReturn1() {
        service.setPriceSellMax(500000);
        double result = service.normalizeSellPrice(new Price(450000, "zł"));
        Assertions.assertEquals(1, result);
    }

    @Test
    void normalizeDistanceFromCenterShouldReturnZero() {
        double result = service.normalizeDistanceFromCenter(3.131);
        Assertions.assertEquals(0, result);
    }

    @Test
    void normalizeDistanceFromCenterShouldReturn1() {
        service.setDistanceFromCenterMax(20);
        double result = service.normalizeDistanceFromCenter(5);
        Assertions.assertEquals(1, result);
    }

    @Test
    void normalizeImagesRatingShouldReturnZero() {
        double result = service.normalizeImagesRating(29.231321);
        Assertions.assertEquals(0, result);
    }

    @Test
    void normalizeImagesRatingShouldReturn1() {
        service.setImagesRatingMin(15.321);
        double result = service.normalizeImagesRating(25.321);
        Assertions.assertEquals(1, result);
    }

    @Test
    void normalizeApartmentSizeShouldReturnZero() {
        double result = service.normalizeApartmentSize(50.45);
        Assertions.assertEquals(0, result);
    }

    @Test
    void normalizeApartmentSizeShouldReturn1() {
        service.setApartmentSizeMin(75);
        double result = service.normalizeApartmentSize(80);
        Assertions.assertEquals(1, result);
    }

    @Test
    void normalizeRoomsQuantityShouldReturnZero() {
        double result = service.normalizeRoomsQuantity(3);
        Assertions.assertEquals(0, result);
    }

    @Test
    void normalizeRoomsQuantityShouldReturn1() {
        service.setRoomsQuantityMin(3);
        double result = service.normalizeRoomsQuantity(4);
        Assertions.assertEquals(1, result);
    }
}