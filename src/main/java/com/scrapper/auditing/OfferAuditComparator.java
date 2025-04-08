package com.scrapper.auditing;

import com.scrapper.entities.Audit;
import com.scrapper.entities.Offer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        OfferAuditComparatorUtil.comparePrice(oldAuditableEntity, newAuditableEntity).ifPresent(audits::add);
        OfferAuditComparatorUtil.compareAttributes(oldAuditableEntity, newAuditableEntity).ifPresent(audits::add);
        return audits;
    }


}
