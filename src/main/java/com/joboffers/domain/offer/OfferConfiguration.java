package com.joboffers.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
class OfferConfiguration {

    @Bean
    OfferFacade offerFacade(OfferRepository offerRepository, JobOfferFetchable jobOfferFetchable) {
        OfferAdder offerAdder = new OfferAdder(offerRepository);
        OffersRetriever offersRetriever = new OffersRetriever(offerRepository);
        OfferFetchable offerFetchable = new OfferFetchable(jobOfferFetchable, offerRepository);
        return new OfferFacade(offersRetriever, offerAdder, offerFetchable);
    }
}
