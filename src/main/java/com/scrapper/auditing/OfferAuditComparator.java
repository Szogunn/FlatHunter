package com.scrapper.auditing;

import com.scrapper.entities.Audit;
import com.scrapper.entities.Offer;
import com.scrapper.entities.Price;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class OfferAuditComparator implements AuditComparator<Offer> {

    @Override
    public List<Audit> audit(Offer oldAuditableEntity, Offer newAuditableEntity) {
        List<Audit> audits = new ArrayList<>();
        if (oldAuditableEntity == null || newAuditableEntity == null) {
            return audits;
        }

        if (!Objects.equals(oldAuditableEntity.getLink(), newAuditableEntity.getLink())){
            return audits;
        }

        comparePrice(oldAuditableEntity, newAuditableEntity).ifPresent(audits::add);
        return audits;
    }

    protected Optional<Audit> comparePrice(Offer oldValue, Offer newValue){
        Price oldPrice = oldValue.getPrice();
        Price newPrice = newValue.getPrice();
        if (Objects.equals(oldPrice, newPrice)){
            return Optional.empty();
        }

        System.out.println("Sprawdzanie price dla oferty " + oldValue.getLink());
        return Optional.of(new Audit(oldValue.getLink(), "price", oldPrice.toString(), newPrice.toString(), LocalDateTime.now()));
    }
}
