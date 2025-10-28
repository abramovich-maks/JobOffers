package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.joboffers.domain.offer.OfferMapper.mapFromOfferToOfferResponseDto;

@AllArgsConstructor
class OfferFetchable {

    private final JobOfferFetchable offerFetcher;
    private final OfferRepository offerRepository;

    List<OfferResponseDto> fetchAllOffersAndSaveAllIfNotExists() {
        List<Offer> jobOffers = fetchOffers();
        final List<Offer> newOffers = filterNewJobOffers(jobOffers);
        List<OfferResponseDto> savedOfferDtos = offerRepository.saveAll(newOffers).stream()
                .map(OfferMapper::mapFromOfferToOfferResponseDto)
                .toList();
        return savedOfferDtos;
    }

    private List<Offer> filterNewJobOffers(final List<Offer> jobOffers) {
        return jobOffers.stream()
                .filter(dto -> !dto.offerUrl().isEmpty())
                .filter(dto -> !offerRepository.existsOfferByOfferUrl(dto.offerUrl()))
                .collect(Collectors.toList());
    }

    private List<Offer> fetchOffers() {
        return offerFetcher.fetchOffers()
                .stream()
                .map(OfferMapper::mapFromGetOfferResponseDtoToOffer)
                .toList();
    }
}
