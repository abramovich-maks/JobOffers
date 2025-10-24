package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.GetOfferResponseDto;

import java.util.List;

public interface JobOfferFetchable {
    List<GetOfferResponseDto> fetchOffers();
}