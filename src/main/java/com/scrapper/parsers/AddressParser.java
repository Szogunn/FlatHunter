package com.scrapper.parsers;

import com.scrapper.entities.Address;
import com.scrapper.searchers.City;

import java.util.Arrays;

public class AddressParser implements DataParser<Address> {
    @Override
    public boolean canParse(String input) {
        return input.contains("ul.") || Arrays.stream(City.values()).anyMatch(city -> input.contains(city.name()));
    }

    @Override
    public Address parse(String input) {
        String[] splitAddress = input.split(",\\s*");
        int length = splitAddress.length;

//        String street = Arrays.stream(splitAddress)
//                .filter(address -> address.contains("ul.") || address.contains("al."))
//                .map(element -> element.replace("ul.", ""))
//                .map(element -> element.replace("al.", ""))
//                .findAny()
//                .orElse(null);

        if (length < 4) {
            throw new RuntimeException();
        }

        String province = null;
        String cityName = null;
        String district = null;
        String estate = null;
        String street = null;
        if (length == 5) {
            province = splitAddress[4];
            cityName = splitAddress[3];
            district = splitAddress[2];
            estate = splitAddress[1];
            street = splitAddress[0];
        } else if (length == 4) {
            province = splitAddress[3];
            cityName = splitAddress[2];
            district = splitAddress[1];
            estate = splitAddress[0];
        }


        Address address = new Address();
        address.setStreet(street);
        address.setEstate(estate);
        address.setDistrict(district);
        address.setCityName(cityName);
        address.setProvince(province);
        // Zwracamy nowy obiekt Address
        return address;
    }
}
