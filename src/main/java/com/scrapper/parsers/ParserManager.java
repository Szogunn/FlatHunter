package com.scrapper.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ParserManager {
    private static final Logger LOG = LoggerFactory.getLogger(ParserManager.class);
    private static final Set<DataParser<?>> parsers = createParsers();

    private static Set<DataParser<?>> createParsers() {
        Set<DataParser<?>> set = new HashSet<>();
        set.add(new ApartmentSizeParser());
        set.add(new PriceParser());
        set.add(new RoomCounterParser());
        set.add(new AddressParser());
        return Set.copyOf(set);
    }

    public static Object parse(String input) {
        if (input == null){
            return null;
        }

        List<DataParser<?>> eligibleParsers = parsers.stream().filter(dataParser -> dataParser.canParse(input)).toList();
        if (eligibleParsers.size() != 1) {
            LOG.error("Nie znaleziono odpowiedniego parsera dla danych wejściowych: {}", input);
            return null;
        }

        return eligibleParsers.get(0).parse(input);
    }
}
