package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.CreateOfferRequestDto;
import com.joboffers.domain.offer.dto.CreateOfferResponseDto;
import com.joboffers.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OfferFacade {

    private final OffersRetriever offersRetriever;
    private final OfferAdder offerAdder;
    private final OfferFetchable offerFetchable;

    public List<OfferResponseDto> findAllOffers() {
        return offersRetriever.findAllOffers();
    }

    public OfferResponseDto findOfferById(String id) {
        return offersRetriever.findOfferById(id);
    }

    public CreateOfferResponseDto saveOffer(CreateOfferRequestDto offer) {
        return offerAdder.addOffer(offer);
    }

    public List<OfferResponseDto> fetchAllOffersAndSaveAllIfNotExists() {
        return offerFetchable.fetchAllOffersAndSaveAllIfNotExists();
    }
}
