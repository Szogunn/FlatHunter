package com.scrapper.repositories;

import com.scrapper.entities.Offer;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferRepository extends MongoRepository<Offer, String> {

    Optional<Offer> findByLink(@Param("link") String link);

    @Aggregation(pipeline = {
            "{ $match: { 'isOnSell': true, 'price.value': { $exists: true } } }",
            "{ $sort: { 'price.value': -1 } }",
            "{ $limit: 1 }"
    })
    Optional<Offer> findMaxPriceSellOffer();

    @Aggregation(pipeline = {
            "{ $match: { 'isOnSell': true, 'price.value': { $exists: true } } }",
            "{ $sort: { 'price.value': 1 } }",
            "{ $limit: 1 }"
    })
    Optional<Offer> findMinPriceSellOffer();

    @Aggregation(pipeline = {
            "{ $match: { 'isOnSell': false, 'price.value': { $exists: true } } }",
            "{ $sort: { 'price.value': -1 } }",
            "{ $limit: 1 }"
    })
    Optional<Offer> findMaxPriceRentalOffer();

    @Aggregation(pipeline = {
            "{ $match: { 'isOnSell': false, 'price.value': { $exists: true } } }",
            "{ $sort: { 'price.value': 1 } }",
            "{ $limit: 1 }"
    })
    Optional<Offer> findMinPriceRentalOffer();

    @Aggregation(pipeline = {
            "{ $sort: { 'distanceFromCenter': -1 } }",
            "{ $limit: 1 }"
    })
    Optional<Offer> findMaxDistanceFromCenterOffer();

    @Aggregation(pipeline = {
            "{ $sort: { 'distanceFromCenter': 1 } }",
            "{ $limit: 1 }"
    })
    Optional<Offer> findMinDistanceFromCenterOffer();

    @Aggregation(pipeline = {
            "{ $sort: { 'imagesRating': -1 } }",
            "{ $limit: 1 }"
    })
    Optional<Offer> findMaxImagesRatingOffer();

    @Aggregation(pipeline = {
            "{ $sort: { 'imagesRating': 1 } }",
            "{ $limit: 1 }"
    })
    Optional<Offer> findMinImagesCenterOffer();
}
