package com.joboffers.domain.offer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryOfferFacadeTestImp implements OfferRepository {

    Map<String, Offer> inMemoryDatabase = new ConcurrentHashMap<>();

    @Override
    public List<Offer> findAllOffers() {
        return inMemoryDatabase.values().stream().toList();
    }

    @Override
    public Optional<Offer> findById(final String id) {
        return Optional.ofNullable(inMemoryDatabase.get(id));
    }

    @Override
    public Offer save(Offer entity) {
        UUID id = UUID.randomUUID();
        Offer offer = new Offer(
                id.toString(),
                entity.title(),
                entity.company(),
                entity.salary(),
                entity.offerUrl()
        );
        inMemoryDatabase.put(id.toString(), offer);
        return offer;
    }

    @Override
    public boolean existsByOfferUrl(final String offerUrl) {
        long count = inMemoryDatabase.values()
                .stream()
                .filter(offer -> offer.offerUrl().equals(offerUrl))
                .count();
        return count == 1;
    }

    @Override
    public List<Offer> saveAll(final List<Offer> offers) {
        return offers.stream()
                .map(this::save)
                .toList();
    }
}
