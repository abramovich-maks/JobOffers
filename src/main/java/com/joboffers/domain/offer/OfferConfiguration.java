package com.joboffers.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
class OfferConfiguration {

    @Bean
    OfferRepository offerRepository() {
        return new OfferRepository() {
            @Override
            public List<Offer> findAllOffers() {
                return List.of();
            }

            @Override
            public Optional<Offer> findById(final String id) {
                return Optional.empty();
            }

            @Override
            public Offer save(final Offer offer) {
                return null;
            }

            @Override
            public boolean existsByOfferUrl(final String offerUrl) {
                return false;
            }

            @Override
            public List<Offer> saveAll(final List<Offer> offers) {
                return List.of();
            }
        };
    }

    @Bean
    OfferFacade offerFacade(OfferRepository offerRepository, JobOfferFetchable jobOfferFetchable) {
        OfferAdder offerAdder = new OfferAdder(offerRepository);
        OffersRetriever offersRetriever = new OffersRetriever(offerRepository);
        OfferFetchable offerFetchable = new OfferFetchable(jobOfferFetchable, offerRepository);
        return new OfferFacade(offersRetriever, offerAdder, offerFetchable);
    }
}
