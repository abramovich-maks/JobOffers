package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.joboffers.domain.offer.OfferMapper.mapFromOfferToOfferResponseDto;

@AllArgsConstructor
class OffersRetriever {

    private final OfferRepository offerRepository;

    List<OfferResponseDto> findAllOffers() {
        return offerRepository.findAll().stream()
                .map(OfferMapper::mapFromOfferToOfferResponseDto)
                .collect(Collectors.toList());
    }

    OfferResponseDto findOfferById(String id) {
        Offer offerById = offerRepository.findById(id)
                .orElseThrow(() -> new OfferNotFoundException(id));
        return mapFromOfferToOfferResponseDto(offerById);
    }
}

