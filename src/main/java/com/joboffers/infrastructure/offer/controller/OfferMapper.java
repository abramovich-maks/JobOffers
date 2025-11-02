package com.joboffers.infrastructure.offer.controller;

import com.joboffers.domain.offer.dto.CreateOfferRequestDto;
import com.joboffers.infrastructure.offer.controller.dto.CreateOfferRequestControllerDto;

class OfferMapper {

    public static CreateOfferRequestDto mapFromCreateOfferRequestControllerDtoToCreateOfferRequestDto(final CreateOfferRequestControllerDto dto) {
        return new CreateOfferRequestDto(dto.title(), dto.company(), dto.salary(), dto.offerUrl());
    }
}
