package com.joboffers.domain.offer.dto;

public record CreateOfferRequestDto(
        String title,
        String company,
        String salary,
        String offerUrl
) {
}
