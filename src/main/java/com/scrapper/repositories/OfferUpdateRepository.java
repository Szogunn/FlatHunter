package com.scrapper.repositories;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.scrapper.auditing.AuditComparator;
import com.scrapper.entities.Audit;
import com.scrapper.entities.Offer;
import com.scrapper.utils.Util;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class OfferUpdateRepository {

    private final MongoTemplate mongoTemplate;
    private final MongoClient mongoClient;
    private final AuditComparator<Offer> auditComparator;

    public OfferUpdateRepository(MongoTemplate mongoTemplate, MongoClient mongoClient, @Qualifier("offerAuditComparator") AuditComparator<Offer> auditComparator) {
        this.mongoTemplate = mongoTemplate;
        this.mongoClient = mongoClient;
        this.auditComparator = auditComparator;
    }

    public Offer updateOfferByAudits(Offer offer, List<Audit> audits) {
        if (Util.isEmpty(audits)){
            return offer;
        }

        Update update = new Update();
        audits.forEach(audit -> update.set(audit.getFieldName(), audit.getNewValue()));
        update.inc("version", 1);

        try (ClientSession session = mongoClient.startSession()) {
            session.startTransaction();
            try {
                MongoTemplate templateWithSession = mongoTemplate.withSession(session);

                Query query = new Query(Criteria.where("_id").is(offer.getLink()));
                FindAndModifyOptions options = FindAndModifyOptions.options().returnNew(true);
                Offer updatedOffer = templateWithSession.findAndModify(query, update, options, Offer.class);

                int version = updatedOffer != null ? updatedOffer.getVersion() : -1;
                audits.forEach(audit -> audit.setEntityVersion(version));
                templateWithSession.insertAll(audits);

                session.commitTransaction();
                return updatedOffer;
            } catch (Exception e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
                session.abortTransaction();
                return null;
            }
        }
    }

    public Offer saveUpdateOffer(Offer offer) {
        Query where = new Query(Criteria.where("_id").is(offer.getLink()));
        Offer existingOffer = mongoTemplate.findOne(where, Offer.class);
        if (existingOffer == null){
            return mongoTemplate.save(offer);
        }

        List<Audit> audits = auditComparator.audit(existingOffer, offer);
        if (Util.isEmpty(audits)){
            return existingOffer;
        }

        return updateOfferByAudits(offer, audits);
    }
}
