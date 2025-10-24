package com.joboffers.domain.offer.dto;

public record GetOfferResponseDto (
        String title,
        String company,
        String salary,
        String offerUrl
){
}
