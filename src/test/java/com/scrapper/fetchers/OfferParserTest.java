package com.scrapper.fetchers;


import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Map;

class OfferParserTest {

    @Test
    void findPagesQuantity() {
        OfferParser offerParser = new OtoDomFetcher();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.otodom.pl/pl/wyniki/sprzedaz/mieszkanie//mazowieckie/warszawa/warszawa/warszawa?&priceMax=800000.0&areaMin=50.0&areaMax=60.0&roomsNumber=%5BTHREE%5D&viewType=listing");
        offerParser.findPagesQuantity(driver);
    }

    @Test
    void findApartmentSize() {
        OfferParser offerParser = new OtoDomFetcher();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.otodom.pl/pl/oferta/wynajme-mieszkanie-aniolki-wrzeszcz-ID4uS5Z");
        double apartmentSize = offerParser.findApartmentSize(driver);
        System.out.println(apartmentSize);
    }

    @Test
    void findRoomsQuantity() {
        OfferParser offerParser = new OtoDomFetcher();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.otodom.pl/pl/oferta/wynajme-mieszkanie-aniolki-wrzeszcz-ID4uS5Z");
        double apartmentSize = offerParser.findRoomsQuantity(driver);
        System.out.println(apartmentSize);
    }

    @Test
    void findDescription() {
        OfferParser offerParser = new OtoDomFetcher();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.otodom.pl/pl/oferta/3-pokoje-swietna-komunikacja-jasien-gdansk-ID4uMwm");
        String description = offerParser.findDescription(driver);
        System.out.println(description);
    }

    @Test
    void findAttributes() {
        OfferParser offerParser = new OtoDomFetcher();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.otodom.pl/pl/oferta/komfortowe-2-pok-z-oddzielna-kuchnia-i-balkonem-ID4uVA8");
        Map<String, String> attributes = offerParser.findAttributes(driver);
        System.out.println(attributes.size());
    }

    @Test
    void findImagesLinks(){
        OfferParser offerParser = new OtoDomFetcher();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.otodom.pl/pl/oferta/dwupokojowy-apartament-zajezdnia-wrzeszcz-gdansk-ID4uUae");
        List<String> imagesLinks = offerParser.findImagesLinks(driver);
        System.out.println(imagesLinks.size());
        driver.quit();
    }


}