package com.scrapper.services;

import com.scrapper.entities.Address;
import com.scrapper.entities.Offer;
import com.scrapper.entities.Price;
import com.scrapper.events.OutgoingEvent;
import com.scrapper.fetchers.OfferParserUtil;
import com.scrapper.repositories.OfferRepository;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OfferService {
    private final WebService webService;
    private final EventsService eventsService;
    private final OfferRepository offerRepository;

    public OfferService(WebService webService, EventsService eventsService, OfferRepository offerRepository) {
        this.webService = webService;
        this.eventsService = eventsService;
        this.offerRepository = offerRepository;
    }

    public List<Offer> parseOffers(String url, String pageSuffix) {
        WebDriver driver = new ChromeDriver();
        driver.get(url);

        boolean onSell = OfferParserUtil.isOnSell(url);
        int pagesNumbers = OfferParserUtil.findPagesQuantity(driver, url);
        List<Offer> offerList = new ArrayList<>();
        for (int i = 1; i <= pagesNumbers; i++) {
            String pageURL = url.concat(pageSuffix + i);
            driver.get(pageURL);

            List<String> links = OfferParserUtil.findLinks(driver, pageURL);
            for (String link : links) {
                driver.get(link);

                Offer offer = new Offer(link);
                offer.setOnSell(onSell);

                String description = OfferParserUtil.findDescription(driver, link);
                offer.setDescription(description);

                Map<String, String> attributes = OfferParserUtil.findAttributes(driver, link);
                offer.setAttributes(attributes);

                Price parsedPrice = OfferParserUtil.findPrice(driver, link);
                offer.setPrice(parsedPrice);

                double apartmentSize = OfferParserUtil.findApartmentSize(driver, link);
                offer.setApartmentSize(apartmentSize);

                int numberOfRooms = OfferParserUtil.findRoomsQuantity(driver, link);
                offer.setNumberOfRooms(numberOfRooms);

                Address address = OfferParserUtil.findAddress(driver, link);
                offer.setAddress(address);

                double distanceFromCenter = webService.findDistanceFromCenter(address);
                offer.setDistanceFromCenter(distanceFromCenter);

                List<String> imagesLinks = OfferParserUtil.findImagesLinks(driver, link);

                try {
                    Offer savedOffer = offerRepository.save(offer);
                    OutgoingEvent event = new OutgoingEvent(savedOffer.getLink(), imagesLinks);
                    eventsService.sendEvent(event);
                    offerList.add(offer);
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        driver.quit();
        return offerList;
    }
}
