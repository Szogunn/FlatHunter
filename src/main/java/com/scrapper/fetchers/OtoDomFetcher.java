package com.scrapper.fetchers;

import com.scrapper.entities.Address;
import com.scrapper.parsers.ParserManager;
import com.scrapper.entities.Price;
import com.scrapper.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;

public class OtoDomFetcher implements OfferParser {

    @Override
    public List<String> findLinks(WebDriver webDriver) {
        return webDriver.findElements(By.xpath("//a[@data-cy=\"listing-item-link\"]"))
                .stream()
                .map(webElement -> webElement.getAttribute("href"))
                .toList();
    }

    @Override
    public Price findPrice(WebDriver webDriver) {
        WebElement priceElement = webDriver.findElement(By.xpath("//strong[@data-cy='adPageHeaderPrice']"));
        String price = priceElement.getText();
        return  (Price) ParserManager.parse(price);
    }

    @Override
    public String findDescription(WebDriver webDriver) {
        WebElement descriptionTag = webDriver.findElement(By.xpath("//div[@data-cy='adPageAdDescription']"));
        return descriptionTag.getText();
//        List<WebElement> descriptionsElements = descriptionTag.findElements(By.tagName("p"));
//        return descriptionsElements
//                .stream()
//                .map(WebElement::getText)
//                .reduce("", (a, b) -> a + b);
    }

    @Override
    public Address findAddress(WebDriver webDriver) {
        WebElement addressElement = webDriver.findElement(By.cssSelector(".css-1jjm9oe"));
        return (Address) ParserManager.parse(addressElement.getText());
    }

    @Override
    public Map<String, String> findAttributes(WebDriver webDriver) {
        // Znajdź główny kontener dla atrybutów
        WebElement attributesTag = webDriver.findElement(By.xpath("//div[@class='css-8mnxk5 e1qcxiy10']"));

        // Znajdź wszystkie elementy div zawierające atrybuty
        List<WebElement> attributesList = attributesTag.findElements(By.xpath(".//div[contains(@class, 'css-1xw0jqp e1qcxiy11')]"));

        Map<String, String> attributes = new HashMap<>();

        for (WebElement attribute : attributesList) {
            // Znajdź wszystkie paragrafy w ramach danego elementu
            List<WebElement> nestedParagraphs = attribute.findElements(By.tagName("p"));

            // Sprawdź, czy są co najmniej dwa paragrafy
            if (nestedParagraphs.size() >= 2) {
                String key = nestedParagraphs.get(0).getText().replace(":", "").trim();
                String value = nestedParagraphs.get(1).getText().trim();
                attributes.put(key, value);
            }
        }

        return attributes;
    }

    @Override
    public double findApartmentSize(WebDriver webDriver) {
        List<WebElement> apartmentSizeElement = webDriver.findElements(By.className("css-1ftqasz"));
        if (Util.isEmpty(apartmentSizeElement)){
            throw new NoSuchElementException("");
        }

        String apartmentSize = apartmentSizeElement.get(0).getText();
        Object parsedObject = ParserManager.parse(apartmentSize);
        return parsedObject != null ? (double) parsedObject : 0;
    }

    @Override
    public int findRoomsQuantity(WebDriver webDriver) {
        List<WebElement> apartmentSizeElement = webDriver.findElements(By.className("css-1ftqasz"));
        if (Util.isEmpty(apartmentSizeElement)){
            throw new NoSuchElementException("");
        }

        String numberOfRooms = apartmentSizeElement.get(1).getText();
        Object parsedObject = ParserManager.parse(numberOfRooms);
        return parsedObject != null ? (int) parsedObject : 0;
    }

    @Override
    public int findPagesQuantity(WebDriver webDriver) {
        WebElement paginationUl = webDriver.findElement(By.cssSelector("ul[data-cy='frontend.search.base-pagination.nexus-pagination']"));

        // Znalezienie wszystkich elementów <li> w tym <ul>
        List<WebElement> paginationLiList = paginationUl.findElements(By.cssSelector("li[aria-disabled='false'][aria-selected='false']"));

        // Znalezienie ostatniego elementu <li> w liście
        WebElement lastLi = paginationLiList.get(paginationLiList.size() - 1);

        // Pobranie tekstu z ostatniego elementu <li>
        return Integer.parseInt(lastLi.getText());
    }

    @Override
    public List<String> findImagesLinks(WebDriver webDriver) {
        List<WebElement> buttons = webDriver.findElements(By.cssSelector(".image-gallery-thumbnail"));
        List<String> imageLinks = new ArrayList<>();

        for (WebElement button : buttons) {
            WebElement img = button.findElement(By.tagName("img"));  // Znajdź tag <img> w przycisku
            String imgSrc = img.getAttribute("src");  // Pobierz atrybut "src"
            imageLinks.add(imgSrc);  // Wyświetl link do obrazu
        }

        return imageLinks;
    }

    @Override
    public boolean isOnSell(String url) throws NoSuchElementException {
        return url != null && url.contains("sprzedaz");
    }
}
