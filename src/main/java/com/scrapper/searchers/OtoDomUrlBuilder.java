package com.scrapper.searchers;

import com.scrapper.utils.Util;

import java.util.Arrays;

public class OtoDomUrlBuilder implements UrlBuilder {

    public static final String BASE_URL = "https://www.otodom.pl/pl/wyniki";

    //https://www.otodom.pl/pl/wyniki/sprzedaz/mieszkanie,3-pokoje/mazowieckie/warszawa/warszawa/warszawa?priceMax=800000.0&areaMin=50.0&areaMax=60.0&roomsNumber=%5BTHREE%5D&viewType=listing
//    https://www.otodom.pl/pl/wyniki/sprzedaz/mieszkanie%2C3-pokoje/mazowieckie/warszawa/warszawa/warszawa?priceMax=800000.0&areaMin=50.0&areaMax=60.0&roomsNumber=%5BTHREE%5D&viewType=listing&page=2
    @Override
    public String buildUrl(OfferSearchCriteria criteria) {
        StringBuilder url = new StringBuilder(BASE_URL);

        String offerType = criteria.isOnSell() ? "/sprzedaz" : "/wynajem";
        url.append(offerType);
        url.append("/mieszkanie"); //todo do wykonania w przyszłości

        if (criteria.getCityLocation() != null) {
            City cityLocation = criteria.getCityLocation();
            String provinceByCity = Province.findProvinceByCity(cityLocation);
            String cityName = cityLocation.name().toLowerCase();

            url.append(String.format("/%s/%s/%s/%s?", provinceByCity, cityName, cityName, cityName));
        }

        if (criteria.getMaxPrice() != 0) {
            url.append("&priceMax=").append(criteria.getMaxPrice());
        }

        if (criteria.getAreaMin() != 0) {
            url.append("&areaMin=").append(criteria.getAreaMin());
        }

        if (criteria.getAreaMax() != 0) {
            url.append("&areaMax=").append(criteria.getAreaMax());
        }

        if (criteria.getRoomsQuantity() != null) {
            int[] roomsNumber = criteria.getRoomsQuantity();
            url.append("&roomsNumber=%5B");
            for (int i = 0; i < roomsNumber.length; i++) {
                int roomNumber = roomsNumber[i];
                url.append(NumberStringRepresentation.getNameByValue(roomNumber));
                if (i != roomsNumber.length - 1){
                    url.append("%2C");
                }
            }
            url.append("%5D");
        }

        url.append("&viewType=listing");
        return url.toString();
    }

    @Override
    public String getPageSuffix() {
        return "&page=";
    }

    enum NumberStringRepresentation {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6);

        final int number;

        NumberStringRepresentation(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }

        public static String getNameByValue(int i){
            NumberStringRepresentation[] values = values();
            for (NumberStringRepresentation value : values){
                if (value.getNumber() == i){
                    return value.name();
                };
            }

            return null;
        }
    }

    enum Province {
        mazowieckie(City.WARSZAWA),
        pomorskie(City.GDANSK);

        private final City city;

        Province(City city) {
            this.city = city;
        }

        public static String findProvinceByCity(City city){
            return Arrays.stream(values())
                    .filter(province -> province.getCity().equals(city))
                    .findAny()
                    .map(String::valueOf)
                    .orElse(Util.EMPTY);
        }

        public City getCity() {
            return city;
        }
    }
}
