package com.scrapper.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrapper.entities.Address;
import com.scrapper.entities.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GeoLocationAPI {
    private static final Logger LOG = LoggerFactory.getLogger(GeoLocationAPI.class);
    public static final String BASE_URL = "https://nominatim.openstreetmap.org/search";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GeoLocationAPI(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
//    https://www.gps-coordinates.net/distance - strona do zweryfikowania poprawności obliczeń z odległością

    public Point findPointByAddress(Address address) {
        if (address == null) {
            return null;
        }

        String addressString = buildAddressApiForm(address);
        String uri = BASE_URL + String.format("?q=%s&format=geojson", addressString);
        try {
            String forObject = restTemplate.getForObject(uri, String.class);
            if (forObject == null) {
                return null;
            }

            JsonNode jsonNode = objectMapper.readTree(forObject);
            JsonNode featuresNode = jsonNode.path("features");
            if (featuresNode.isEmpty()){
                LOG.debug("Node {} does not have features path. Provided link {}", jsonNode.asText(), uri);
                return null;
            }

            JsonNode coordinatesNode = featuresNode.get(0).path("geometry").path("coordinates");
            if (!coordinatesNode.isArray() || coordinatesNode.size() != 2) {
                LOG.debug("Node {} does not match to array element. Provided link {}", coordinatesNode.asText(), uri);
                return null;
            }

            double lon = coordinatesNode.get(0).asDouble();
            double lat = coordinatesNode.get(1).asDouble();
            return new Point(lat, lon);
        } catch (Exception e) {
            LOG.error("Error during mapping web api response on address {}", addressString, e);
            return null;
        }
    }

    public static String buildAddressApiForm(Address address) {
        if (address == null){
            return Util.EMPTY;
        }

        String firstElement = address.getStreet() != null ? address.getStreet() : address.getEstate();
        if (firstElement == null) {
            firstElement = address.getDistrict();
        }

        firstElement = firstElement.replace("ul.", "").trim();
        firstElement = firstElement.replace("al.", "").trim();
        firstElement = firstElement.replaceAll(" ", "+");
        return firstElement + "+" + address.getCityName();
    }

}
