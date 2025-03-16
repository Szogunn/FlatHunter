package com.scrapper.services;

import com.scrapper.entities.Offer;
import com.scrapper.events.ImageRatingIncomingEvent;
import com.scrapper.events.OutgoingEvent;
import com.scrapper.repositories.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventsService {
    private static final Logger LOG = LoggerFactory.getLogger(EventsService.class);

    private final RabbitTemplate rabbitTemplate;
    private final OfferRepository offerRepository;
    private final RecommendationService recommendationService;

    public EventsService(RabbitTemplate rabbitTemplate, OfferRepository offerRepository, RecommendationService recommendationService) {
        this.rabbitTemplate = rabbitTemplate;
        this.offerRepository = offerRepository;
        this.recommendationService = recommendationService;
    }

    public void sendEvent(OutgoingEvent outgoingEvent) {
        try {
            rabbitTemplate.convertAndSend("outgoingQueue", outgoingEvent);
        } catch (AmqpException exception){
            LOG.error("Exception when trying to send event to outgoingQueue", exception);
        }

    }

    @RabbitListener(queues = "resultQueue")
    public void handleIncomingImagesRating(ImageRatingIncomingEvent incomingEvent) {
        Optional<Offer> optionalOffer = offerRepository.findByLink(incomingEvent.getOfferLink());
        if (optionalOffer.isEmpty()){
            LOG.error("Offer not found for link: {}", incomingEvent.getOfferLink());
            return;
        }

        Offer offer = optionalOffer.get();
        offer.setImagesRating(incomingEvent.getRating());

        double score = recommendationService.calculateOfferAttractiveness(offer);
        offer.setOfferScore(score);
        try {
            offerRepository.save(offer);
        } catch (OptimisticLockingFailureException lockingFailureException){
            LOG.error("Exception on listener", lockingFailureException);
        }
    }
}
