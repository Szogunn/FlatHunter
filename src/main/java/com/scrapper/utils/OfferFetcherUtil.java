package com.scrapper.utils;

import com.scrapper.entities.Address;
import com.scrapper.entities.Offer;
import com.scrapper.entities.Price;
import com.scrapper.events.OutgoingEvent;
import com.scrapper.fetchers.OfferParserUtil;
import com.scrapper.repositories.OfferUpdateRepository;
import com.scrapper.searchers.City;
import com.scrapper.searchers.OfferSearchCriteria;
import com.scrapper.searchers.UrlBuilder;
import com.scrapper.searchers.UrlBuilderFactory;
import com.scrapper.services.EventsService;
import com.scrapper.services.WebService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public class OfferFetcherUtil {
    private final WebService webService;
    private final EventsService eventsService;
    private final OfferUpdateRepository offerUpdateRepository;

    @Value("${task.parameter.serviceName:OtoDom}")
    private String taskServiceNameParameter;

    OfferSearchCriteria criteria = new OfferSearchCriteria.Builder(false)
            .cityLocation(City.GDANSK)
            .maxPrice(3000.0)
            .areaMin(45.0)
            .roomsQuantity(new int[] {2,3})
            .build();

    public OfferFetcherUtil(WebService webService, EventsService eventsService, OfferUpdateRepository offerUpdateRepository) {
        this.webService = webService;
        this.eventsService = eventsService;
        this.offerUpdateRepository = offerUpdateRepository;
    }

    @Scheduled(fixedDelay = 900000)
    public void parseOffers() {
        UrlBuilder urlBuilder = UrlBuilderFactory.getUrlBuilder(taskServiceNameParameter);
        String url = urlBuilder.buildUrl(criteria);
        String pageSuffix = urlBuilder.getPageSuffix();

        WebDriver driver = new ChromeDriver();
        driver.get(url);

        boolean onSell = OfferParserUtil.isOnSell(url);
        int pagesNumbers = OfferParserUtil.findPagesQuantity(driver, url);

        for (int i = 1; i <= pagesNumbers; i++) {
            String pageURL = url.concat(pageSuffix + i);
            driver.get(pageURL);

            List<String> links = OfferParserUtil.findLinks(driver, pageURL);
            for (String link : links) {
                driver.get(link);

                Offer offer = new Offer(link, LocalDateTime.now());
                offer.setOnSell(onSell);

                String description = OfferParserUtil.findDescription(driver, link);
                offer.setDescription(description);

                Map<String, String> attributes = OfferParserUtil.findAttributes(driver, link);
                offer.setAttributes(attributes);

                Price parsedPrice = onSell ? OfferParserUtil.findPrice(driver, link) : OfferParserUtil.findRentPrice(driver, link);
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

                if (!validOfferBeforeSave(offer)){
                    continue;
                }

                Offer savedOffer = offerUpdateRepository.saveUpdateOffer(offer);
                if (savedOffer == null){
                    System.out.println("Nie udało się zaktualizować ");
                } else if (!Util.isEmpty(imagesLinks)) {
                    eventsService.sendEvent(new OutgoingEvent(offer.getLink(), savedOffer.getVersion(), imagesLinks));
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        driver.quit();
    }

    private boolean validOfferBeforeSave(Offer offer) {
        if (offer == null){
            return false;
        }

        if (offer.getPrice() == null){
            return false;
        }

        return true;
    }
}
