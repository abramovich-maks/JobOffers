package com.joboffers.domain.offer;

import java.util.List;
import java.util.Optional;

interface OfferRepository {

    List<Offer> findAllOffers();

    Optional<Offer> findById(String id);

    Offer save(Offer offer);

    boolean existsByOfferUrl(String offerUrl);

    List<Offer> saveAll(List<Offer> offers);

}
