package com.joboffers.domain.offer;


import com.joboffers.domain.offer.dto.GetOfferResponseDto;

import java.util.List;

public class InMemoryFetcherTestImpl implements JobOfferFetchable {

    List<GetOfferResponseDto> listOfOffers;

    InMemoryFetcherTestImpl(List<GetOfferResponseDto> listOfOffers) {
        this.listOfOffers = listOfOffers;
    }

    @Override
    public List<GetOfferResponseDto> fetchOffers() {
        return listOfOffers;
    }
}