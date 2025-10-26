package com.joboffers.domain.offer.dto;

import lombok.Builder;

@Builder
public record GetOfferResponseDto (
        String title,
        String company,
        String salary,
        String offerUrl
){
}
