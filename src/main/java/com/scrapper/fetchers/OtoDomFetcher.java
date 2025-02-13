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
import java.util.stream.Collectors;

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
        String text = descriptionTag.getAttribute("innerText");
        return text == null ? Util.EMPTY :
                Arrays.stream(text.split("\\R"))
                .filter(line -> !line.trim().isEmpty())
                .collect(Collectors.joining("\n"));
    }

    @Override
    public Address findAddress(WebDriver webDriver) {
        WebElement addressElement = webDriver.findElement(By.cssSelector(".css-1jjm9oe"));
        return (Address) ParserManager.parse(addressElement.getText());
    }

    @Override
    public Map<String, String> findAttributes(WebDriver webDriver) {
        List<WebElement> attributesList = webDriver.findElements(By.xpath(".//div[contains(@class, 'css-1xw0jqp eows69w1')]"));

        Map<String, String> attributes = new HashMap<>();

        for (WebElement attribute : attributesList) {
            // Znajdź wszystkie paragrafy w ramach danego elementu
            List<WebElement> nestedParagraphs = attribute.findElements(By.tagName("p"));

            // Sprawdź, czy są co najmniej dwa paragrafy
            if (Util.isEmpty(nestedParagraphs) || nestedParagraphs.size() < 2) {
                continue;
            }

            String key = nestedParagraphs.get(0).getAttribute("innerText");
            String value = nestedParagraphs.get(1).getAttribute("innerText");
            if (key != null && value != null) {
                key = key.replace(":", "").trim();
                value = value.trim();
                attributes.put(key, value);
            }
        }

        return attributes;
    }

    @Override
    public double findApartmentSize(WebDriver webDriver) {
        List<WebElement> featuresList = webDriver.findElements(By.cssSelector("button.eezlw8k1.css-1nk40gi"));
        String svg = "<path fill=\"currentColor\" d=\"M19.983 18.517 5.439 3.973h2.544v-2H2.025V8h2V5.387L18.638 20h-2.614v2h5.959v-6.028h-2v2.544ZM3.996 12.001h-2v2h2v-2ZM1.996 16.001h2v2h-2v-2ZM3.996 20.001h-2v2h2v-2ZM5.997 20.001h2v2h-2v-2ZM11.99 20.001h-2v2h2v-2ZM20.003 10.001h2v2h-2v-2ZM22.003 6.001h-2v2h2v-2ZM20.003 2.001h2v2h-2v-2ZM18.003 2.001h-2v2h2v-2ZM11.989 2.001h2v2h-2v-2Z\"></path>";

        List<WebElement> matchedFeature = new ArrayList<>();
        for (WebElement feature : featuresList) {
            try {

                WebElement svgElement = feature.findElement(By.tagName("svg"));
                if (svg.equals(svgElement.getAttribute("innerHTML"))){
                    matchedFeature.add(feature);
                }

            } catch (Exception e) {
                System.out.println("Nie znaleziono znacznika SVG w jednym z przycisków.");
            }
        }

        if (matchedFeature.size() != 1){
            throw new NoSuchElementException("");
        }

        String apartmentSize = matchedFeature.get(0).getText();
        Object parsedObject = ParserManager.parse(apartmentSize);
        return parsedObject != null ? (double) parsedObject : 0;
    }

    @Override
    public int findRoomsQuantity(WebDriver webDriver) {
        List<WebElement> featuresList = webDriver.findElements(By.cssSelector("button.eezlw8k1.css-1nk40gi"));
        String svg = "<path fill=\"currentColor\" d=\"M21 10.958h-7.958v-8l-1-1h-9l-1 1v18l1 1H21l1-1v-9l-1-1Zm-1 9h-6.958v-2H11v2H4.042v-7h2v-2h-2v-7h7v7h-2v2H11v2h2.042v-2H20v7Z\"></path>";

        List<WebElement> matchedFeature = new ArrayList<>();
        for (WebElement feature : featuresList) {
            try {

                WebElement svgElement = feature.findElement(By.tagName("svg"));
                if (svg.equals(svgElement.getAttribute("innerHTML"))){
                    matchedFeature.add(feature);
                }

            } catch (Exception e) {
                System.out.println("Nie znaleziono znacznika SVG w jednym z przycisków.");
            }
        }

        if (matchedFeature.size() != 1){
            throw new NoSuchElementException("");
        }

        String numberOfRooms = matchedFeature.get(0).getText();
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

        if (imageLinks.isEmpty()){
            throw new NoSuchElementException("Not found any images");
        }

        return imageLinks;
    }

    @Override
    public boolean isOnSell(String url) throws NoSuchElementException {
        return url != null && url.contains("sprzedaz");
    }
}
