package com.scrapper.auditing;

import com.scrapper.entities.Audit;
import com.scrapper.entities.Offer;
import com.scrapper.entities.Price;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class OfferAuditComparatorUtil {

    public static Optional<Audit> comparePrice(Offer oldValue, Offer newValue){
        Price oldPrice = oldValue.getPrice();
        Price newPrice = newValue.getPrice();
        if (Objects.equals(oldPrice, newPrice)){
            return Optional.empty();
        }

        return Optional.of(new Audit(oldValue.getLink(), "price", oldPrice, newPrice, LocalDateTime.now()));
    }

    public static Optional<Audit> compareAttributes(Offer oldValue, Offer newValue){
        Map<String, String> oldAttributes = oldValue.getAttributes();
        Map<String, String> newAttributes = newValue.getAttributes();
        if (oldAttributes.entrySet().stream().allMatch(el -> Objects.equals(el.getValue(), newAttributes.get(el.getKey())))){
            return Optional.empty();
        }

        return Optional.of(new Audit(oldValue.getLink(), "attributes", oldAttributes, newAttributes, LocalDateTime.now()));
    }
}
