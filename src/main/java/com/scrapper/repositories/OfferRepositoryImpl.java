package com.scrapper.repositories;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.scrapper.entities.Offer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class OfferRepositoryImpl {

    private final MongoTemplate mongoTemplate;
    private final MongoClient mongoClient;

    public OfferRepositoryImpl(MongoTemplate mongoTemplate, MongoClient mongoClient) {
        this.mongoTemplate = mongoTemplate;
        this.mongoClient = mongoClient;
    }

    public Offer findOfferByLink(String link) {
        ClientSession session = mongoClient.startSession();
        return mongoTemplate.withSession(() -> session)
                .execute(action -> {
                    session.startTransaction();

                    Query query = query(where("_id").is(link));
                    return action.findOne(query, Offer.class);
                }, ClientSession::close);
    }
}
