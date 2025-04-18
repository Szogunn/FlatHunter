package com.scrapper.controllers;

import com.scrapper.entities.Offer;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api")
public class OfferController {

    private final MongoTemplate mongoTemplate;

    public OfferController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping(path = "/best")
    List<Offer> findBestOffers(){
        Query query = new Query()
                .with(Sort.by(Sort.Direction.DESC, "offerScore"))
                .limit(10);
        return mongoTemplate.find(query, Offer.class);
    }

    @GetMapping(path = "/worst")
    List<Offer> findWorstOffers(){
        Query query = new Query()
                .with(Sort.by(Sort.Direction.ASC, "offerScore"))
                .addCriteria(Criteria.where("offerScore").gt(0))
                .limit(10);
        return mongoTemplate.find(query, Offer.class);
    }
}
