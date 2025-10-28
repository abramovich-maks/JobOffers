package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
class OfferFetchable {

    private final JobOfferFetchable offerFetcher;
    private final OfferRepository offerRepository;

    List<OfferResponseDto> fetchAllOffersAndSaveAllIfNotExists() {
        List<Offer> jobOffers = fetchOffers();
        final List<Offer> newOffers = filterNewJobOffers(jobOffers);
        return jobOffers.stream().map(OfferMapper::mapFromOfferToOfferResponseDto)
                .toList();
//        return offerRepository.saveAll(newOffers).stream()
//                .map(OfferMapper::mapFromOfferToOfferResponseDto)
//                .toList();
    }

    private List<Offer> filterNewJobOffers(final List<Offer> jobOffers) {
        return jobOffers.stream()
                .filter(dto -> !dto.offerUrl().isEmpty())
                .filter(dto -> !offerRepository.existsByOfferUrl(dto.offerUrl()))
                .collect(Collectors.toList());
    }

    private List<Offer> fetchOffers() {
        return offerFetcher.fetchOffers()
                .stream()
                .map(OfferMapper::mapFromGetOfferResponseDtoToOffer)
                .toList();
    }
}
