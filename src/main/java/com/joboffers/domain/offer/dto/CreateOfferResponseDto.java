package com.joboffers.domain.offer.dto;

import lombok.Builder;

@Builder
public record CreateOfferResponseDto(
        OfferResponseDto offer,
        String message
) {
}
