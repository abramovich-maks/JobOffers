package com.joboffers.domain.offer.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record OfferResponseDto(
        String offerId,
        String title,
        String company,
        String salary,
        String offerUrl
) implements Serializable {
}
