package com.scrapper.repositories;

import com.scrapper.entities.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferRepository extends MongoRepository<Offer, String> {

    Optional<Offer> findByLink(@Param("link") String link);
}
