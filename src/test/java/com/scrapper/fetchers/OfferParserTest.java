package com.scrapper.fetchers;


import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

class OfferParserTest {

    @Test
    void findPagesQuantity() {
        OfferParser offerParser = new OtoDomFetcher();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.otodom.pl/pl/wyniki/sprzedaz/mieszkanie//mazowieckie/warszawa/warszawa/warszawa?&priceMax=800000.0&areaMin=50.0&areaMax=60.0&roomsNumber=%5BTHREE%5D&viewType=listing");
        offerParser.findPagesQuantity(driver);
    }

}