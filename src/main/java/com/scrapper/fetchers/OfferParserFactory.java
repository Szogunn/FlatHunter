package com.scrapper.fetchers;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OfferParserFactory {

    private static final Map<String, OfferParser> parsers = new HashMap<>();

    public OfferParserFactory() {
        // Rejestracja parserów dla konkretnych serwisów
        parsers.put("www.otodom.pl", new OtoDomFetcher());
    }

    public static OfferParser getParser(String url) {
        // Sprawdzenie, czy URL pasuje do znanego serwisu
        for (String domain : parsers.keySet()) {
            if (url.contains(domain)) {
                return parsers.get(domain);
            }
        }

        throw new IllegalArgumentException("Brak parsera dla tego URL: " + url);
    }
}

