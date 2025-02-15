package com.scrapper.fetchers;

import com.scrapper.entities.Address;
import com.scrapper.entities.Price;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

public interface OfferParser {
    List<String> findLinks(WebDriver webDriver) throws NoSuchElementException;
    Price findPrice(WebDriver webDriver) throws NoSuchElementException;
    String findDescription(WebDriver webDriver) throws NoSuchElementException;
    Address findAddress(WebDriver webDriver) throws NoSuchElementException;
    Map<String, String> findAttributes(WebDriver webDriver) throws NoSuchElementException;
    double findApartmentSize(WebDriver webDriver) throws NoSuchElementException;
    int findRoomsQuantity(WebDriver webDriver) throws NoSuchElementException;
    int findPagesQuantity(WebDriver webDriver) throws NoSuchElementException;
    List<String> findImagesLinks(WebDriver webElement) throws NoSuchElementException;
    boolean isOnSell(String url) throws NoSuchElementException;
    Price findRentPrice(WebDriver webDriver) throws NoSuchElementException;
}
