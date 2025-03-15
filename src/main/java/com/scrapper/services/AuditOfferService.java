package com.scrapper.services;

import com.scrapper.entities.Audit;
import com.scrapper.auditing.AuditComparator;
import com.scrapper.entities.Offer;
import com.scrapper.repositories.AuditRepository;
import com.scrapper.repositories.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuditOfferService {

    private final AuditComparator<Offer> auditComparator;
    private final OfferRepository offerRepository;
    private final AuditRepository auditRepository;

    public AuditOfferService(AuditComparator<Offer> auditComparator, OfferRepository offerRepository, AuditRepository auditRepository) {
        this.auditComparator = auditComparator;
        this.offerRepository = offerRepository;
        this.auditRepository = auditRepository;
    }

    List<Audit> audit(Offer offer) {
        Optional<Offer> existingOffer = offerRepository.findByLink(offer.getLink());
        if (existingOffer.isEmpty()){
            return null;
        }

        Offer oldOffer = existingOffer.get();
        List<Audit> audits = auditComparator.audit(oldOffer, offer);
        if (!audits.isEmpty()) {
            auditRepository.saveAll(audits);
        }

        return audits;
    }
}
