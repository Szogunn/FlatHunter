package com.scrapper.fetchers;

import com.scrapper.entities.Address;
import com.scrapper.entities.Price;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class OfferParserUtil {
    private static final Logger LOG = LoggerFactory.getLogger(OfferParserUtil.class);

    public static Price findPrice(WebDriver webDriver, String url) {
        OfferParser parser = OfferParserFactory.getParser(url);
        if (parser == null){
            return null;
        }

        try {
            return parser.findPrice(webDriver);
        } catch (NoSuchElementException exception){
            LOG.error("Cannot find price from url {} by parser {}", url, parser.getClass().getSimpleName());
            return null;
        }
    }

    public static Map<String, String> findAttributes(WebDriver webDriver, String url){
        OfferParser parser = OfferParserFactory.getParser(url);
        if (parser == null){
            return null;
        }

        try {
            return parser.findAttributes(webDriver);
        }catch (NoSuchElementException exception){
            LOG.error("Cannot find attributes from url {} by parser {}", url, parser.getClass().getSimpleName());
            return null;
        }
    }

    public static List<String> findLinks(WebDriver webDriver, String url) {
        OfferParser parser = OfferParserFactory.getParser(url);
        if (parser == null){
            return List.of();
        }

        try {
            return parser.findLinks(webDriver);
        }catch (NoSuchElementException exception){
            LOG.error("Cannot find links from url {} by parser {}", url, parser.getClass().getSimpleName());
            return List.of();
        }
    }

    public static String findDescription(WebDriver webDriver, String url) {
        OfferParser parser = OfferParserFactory.getParser(url);
        if (parser == null){
            return null;
        }

        try {
            return parser.findDescription(webDriver);
        }catch (NoSuchElementException exception){
            LOG.error("Cannot find description from url {} by parser {}", url, parser.getClass().getSimpleName());
            return null;
        }
    }

    public static Address findAddress(WebDriver webDriver, String url) {
        OfferParser parser = OfferParserFactory.getParser(url);
        if (parser == null){
            return null;
        }

        try {
            return parser.findAddress(webDriver);
        }catch (NoSuchElementException exception){
            LOG.error("Cannot find address from url {} by parser {}", url, parser.getClass().getSimpleName());
            return null;
        }
    }

    public static double findApartmentSize(WebDriver webDriver, String url) {
        OfferParser parser = OfferParserFactory.getParser(url);
        if (parser == null){
            return -1.0;
        }

        try {
            return parser.findApartmentSize(webDriver);
        }catch (NoSuchElementException exception){
            LOG.error("Cannot find apartment size from url {} by parser {}", url, parser.getClass().getSimpleName());
            return -1.0;
        }
    }

    public static int findRoomsQuantity(WebDriver webDriver, String url) {
        OfferParser parser = OfferParserFactory.getParser(url);
        if (parser == null){
            return -1;
        }

        try {
            return parser.findRoomsQuantity(webDriver);
        }catch (NoSuchElementException exception){
            LOG.error("Cannot find rooms quantity from url {} by parser {}", url, parser.getClass().getSimpleName());
            return -1;
        }
    }

    public static int findPagesQuantity(WebDriver webDriver, String url) {
        OfferParser parser = OfferParserFactory.getParser(url);
        if (parser == null){
            return -1;
        }

        try {
            return parser.findPagesQuantity(webDriver);
        }catch (NoSuchElementException exception){
            LOG.error("Cannot find pages num from url {} by parser {}", url, parser.getClass().getSimpleName());
            return 1;
        }
    }

    public static List<String> findImagesLinks(WebDriver webDriver, String url) {
        OfferParser parser = OfferParserFactory.getParser(url);
        if (parser == null){
            return null;
        }

        try {
            return parser.findImagesLinks(webDriver);
        }catch (NoSuchElementException exception){
            LOG.error("Cannot find images links from url {} by parser {}", url, parser.getClass().getSimpleName());
            return null;
        }
    }

    public static boolean isOnSell(String url){
        OfferParser parser = OfferParserFactory.getParser(url);
        if (parser == null){
            return false;
        }

        try {
            return parser.isOnSell(url);
        }catch (NoSuchElementException exception){
            LOG.error("Cannot find offer type (sell/rent) from url {} by parser {}", url, parser.getClass().getSimpleName());
            return false;
        }
    }

}
