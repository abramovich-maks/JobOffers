package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.CreateOfferRequestDto;
import com.joboffers.domain.offer.dto.CreateOfferResponseDto;
import com.joboffers.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import static com.joboffers.domain.offer.OfferMapper.mapFromCreateOfferRequestDtoToOffer;
import static com.joboffers.domain.offer.OfferMapper.mapFromOfferToOfferResponseDto;

@AllArgsConstructor
@Log4j2
class OfferAdder {

    private final OfferRepository offerRepository;

    CreateOfferResponseDto addOffer(final CreateOfferRequestDto dto) {
        if (offerRepository.existsOfferByOfferUrl(dto.offerUrl())) {
            throw new DuplicateKeyException(dto.offerUrl());
        }
        Offer offer = mapFromCreateOfferRequestDtoToOffer(dto);
        Offer save = offerRepository.save(offer);
        OfferResponseDto createdOffer = mapFromOfferToOfferResponseDto(save);
        log.info("Saved offer with id: {} and title: {}", save.offerId(), save.title());
        return CreateOfferResponseDto.builder()
                .offer(createdOffer)
                .message("Offer created.")
                .build();
    }
}
