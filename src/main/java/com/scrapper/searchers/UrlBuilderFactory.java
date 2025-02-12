package com.scrapper.searchers;

public class UrlBuilderFactory {

    public static UrlBuilder getUrlBuilder(String serviceName) {
        return switch (serviceName) {
            case "OtoDom" -> new OtoDomUrlBuilder();
            default -> throw new IllegalArgumentException("Nieznany serwis: " + serviceName);
        };
    }
}
