package com.scrapper;

import com.scrapper.entities.Offer;
import com.scrapper.searchers.City;
import com.scrapper.searchers.OfferSearchCriteria;
import com.scrapper.searchers.UrlBuilder;
import com.scrapper.searchers.UrlBuilderFactory;
import com.scrapper.services.OfferService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        OfferService offerService = context.getBean(OfferService.class);

        OfferSearchCriteria criteria = new OfferSearchCriteria();
        criteria.setOnSell(false);
        criteria.setCityLocation(City.GDANSK);
        criteria.setMaxPrice(3000.0);
        criteria.setAreaMin(45.0);
//        criteria.setAreaMax(70.0);
        int[] roomNumbers = {2,3};
        criteria.setRoomsNumber(roomNumbers);

        UrlBuilder urlBuilder = UrlBuilderFactory.getUrlBuilder("OtoDom");
        String finalUrl = urlBuilder.buildUrl(criteria);
        String pageSuffix = urlBuilder.getPageSuffix();

        List<Offer> offerList = offerService.parseOffers(finalUrl, pageSuffix);
        System.out.println(offerList.size());
    }
}
