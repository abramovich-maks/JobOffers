package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.CreateOfferRequestDto;
import com.joboffers.domain.offer.dto.GetOfferResponseDto;
import com.joboffers.domain.offer.dto.OfferResponseDto;

class OfferMapper {

    static Offer mapFromGetOfferResponseDtoToOffer(final GetOfferResponseDto dto) {
        return Offer.builder()
                .title(dto.title())
                .company(dto.company())
                .salary(dto.salary())
                .offerUrl(dto.offerUrl())
                .build();
    }

    static OfferResponseDto mapFromOfferToOfferResponseDto(final Offer save) {
        return OfferResponseDto.builder()
                .offerId(save.offerId())
                .title(save.title())
                .company(save.company())
                .salary(save.salary())
                .offerUrl(save.offerUrl())
                .build();
    }

    static Offer mapFromCreateOfferRequestDtoToOffer(final CreateOfferRequestDto dto) {
        return Offer.builder()
                .title(dto.title())
                .company(dto.company())
                .salary(dto.salary())
                .offerUrl(dto.offerUrl())
                .build();
    }
}
