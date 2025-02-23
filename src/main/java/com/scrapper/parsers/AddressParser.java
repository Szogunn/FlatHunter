package com.scrapper.parsers;

import com.scrapper.entities.Address;
import com.scrapper.searchers.City;

import java.text.Normalizer;
import java.util.Arrays;

public class AddressParser implements DataParser<Address> {
    @Override
    public boolean canParse(String input) {
        String normalizedInput = Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}", "").toUpperCase();
        return input.contains("ul.") || Arrays.stream(City.values()).anyMatch(city -> normalizedInput.contains(city.name()));
    }

    @Override
    public Address parse(String input) {
        String[] splitAddress = input.split(",\\s*");
        int length = splitAddress.length;

        if (length == 5) {
            return  new Address(splitAddress[4], splitAddress[3], splitAddress[2], splitAddress[1], splitAddress[0]);
        } else if (length == 4) {
            return  new Address(splitAddress[3], splitAddress[2], splitAddress[1], splitAddress[0]);
        } else if (length == 3) {
            return  new Address(splitAddress[2], splitAddress[1], splitAddress[0]);
        }

        return null;
    }
}
