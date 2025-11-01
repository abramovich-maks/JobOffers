package com.joboffers.infrastructure.offer.controller;

import com.joboffers.domain.offer.OfferFacade;
import com.joboffers.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
class OfferRestController {

    private final OfferFacade offerFacade;

    @GetMapping("/offers")
    public ResponseEntity<List<OfferResponseDto>> findAllOffers() {
        List<OfferResponseDto> allOffers = offerFacade.findAllOffers();
        List<OfferResponseDto> offersDtoList = allOffers.stream().map(offer -> OfferResponseDto.builder()
                        .offerId(offer.offerId())
                        .title(offer.title())
                        .company(offer.company())
                        .salary(offer.salary())
                        .offerUrl(offer.offerUrl())
                        .build())
                .toList();
        return ResponseEntity.ok(offersDtoList);
    }

    @GetMapping("/offers/{offerId}")
    public ResponseEntity<OfferResponseDto> findOfferById(@PathVariable String offerId) {
        OfferResponseDto offerById = offerFacade.findOfferById(offerId);
        return ResponseEntity.ok(offerById);
    }
}
