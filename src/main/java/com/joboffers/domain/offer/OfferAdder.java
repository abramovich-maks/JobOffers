package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.CreateOfferRequestDto;
import com.joboffers.domain.offer.dto.CreateOfferResponseDto;
import com.joboffers.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;

import static com.joboffers.domain.offer.OfferMapper.mapFromCreateOfferRequestDtoToOffer;
import static com.joboffers.domain.offer.OfferMapper.mapFromOfferToOfferResponseDto;

@AllArgsConstructor
class OfferAdder {

    private final OfferRepository offerRepository;

    CreateOfferResponseDto addOffer(final CreateOfferRequestDto dto) {
        Offer offer = mapFromCreateOfferRequestDtoToOffer(dto);
        Offer save = offerRepository.save(offer);
        OfferResponseDto createdOffer = mapFromOfferToOfferResponseDto(save);
        return CreateOfferResponseDto.builder()
                .offer(createdOffer)
                .message("Offer created.")
                .build();
    }
}
