package com.joboffers.domain.offer;

import org.springframework.data.mongodb.repository.MongoRepository;

interface OfferRepository extends MongoRepository<Offer, String> {
    boolean existsOfferByOfferUrl(String offerUrl);
}
