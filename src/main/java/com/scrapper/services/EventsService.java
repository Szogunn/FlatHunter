package com.scrapper.services;

import com.rabbitmq.client.Channel;
import com.scrapper.entities.Offer;
import com.scrapper.events.ImageRatingIncomingEvent;
import com.scrapper.events.OutgoingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EventsService {
    private static final Logger LOG = LoggerFactory.getLogger(EventsService.class);

    private final RabbitTemplate rabbitTemplate;
    private final MongoTemplate mongoTemplate;
    private final RecommendationService recommendationService;

    public EventsService(RabbitTemplate rabbitTemplate, MongoTemplate mongoTemplate, RecommendationService recommendationService) {
        this.rabbitTemplate = rabbitTemplate;
        this.mongoTemplate = mongoTemplate;
        this.recommendationService = recommendationService;
    }

    public void sendEvent(OutgoingEvent outgoingEvent) {
        try {
            rabbitTemplate.convertAndSend("outgoingQueue", outgoingEvent);
        } catch (AmqpException exception){
            LOG.error("Exception when trying to send event to outgoingQueue", exception);
        }

    }

    @RabbitListener(queues = "resultQueue" , ackMode = "MANUAL")
    public void handleIncomingImagesRating(ImageRatingIncomingEvent incomingEvent, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,  Channel channel) {
        Query query = new Query(Criteria.where("_id").is(incomingEvent.getOfferLink())
                .and("version").is(incomingEvent.getOfferVersion()) //verify version
                .and("imagesRating").ne(incomingEvent.getRating())); //modify only if imagesRating has changed

        Update update = Update.update("imagesRating", incomingEvent.getRating());
        FindAndModifyOptions options = FindAndModifyOptions.options().returnNew(true);

        Offer liveOffer;
        try {
            try {
                liveOffer = mongoTemplate.findAndModify(query, update, options, Offer.class);
                channel.basicAck(deliveryTag, false);
                if (liveOffer == null){
                    LOG.info(" nie znaleziono {}", incomingEvent.getOfferLink());
                    return;
                }
            } catch (Exception ex) {
                LOG.error("Failed processing incoming event {}. Event has benn requeue", incomingEvent.getOfferLink());
                channel.basicNack(deliveryTag, false, true);
                return;
            }
        } catch (IOException e){
            LOG.error("Failed processing incoming event {}. Event has lost.", incomingEvent.getOfferLink(), e); //TODO
            return;
        }

        double attractiveness = recommendationService.calculateOfferAttractiveness(liveOffer);
        Query queryAttractiveness = new Query(Criteria.where("_id").is(incomingEvent.getOfferLink())
                .and("version").is(incomingEvent.getOfferVersion())); //verify version
        Update updateAttractiveness = Update.update("offerScore", attractiveness);
        mongoTemplate.updateFirst(queryAttractiveness, updateAttractiveness, Offer.class);
    }
}
