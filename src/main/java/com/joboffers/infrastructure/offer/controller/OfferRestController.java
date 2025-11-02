package com.joboffers.infrastructure.offer.controller;

import com.joboffers.domain.offer.OfferFacade;
import com.joboffers.domain.offer.dto.CreateOfferRequestDto;
import com.joboffers.domain.offer.dto.CreateOfferResponseDto;
import com.joboffers.domain.offer.dto.OfferResponseDto;
import com.joboffers.infrastructure.offer.controller.dto.CreateOfferRequestControllerDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.joboffers.infrastructure.offer.controller.OfferMapper.mapFromCreateOfferRequestControllerDtoToCreateOfferRequestDto;

@RestController
@AllArgsConstructor
class OfferRestController {

    private final OfferFacade offerFacade;

    @GetMapping("/offers")
    public ResponseEntity<List<OfferResponseDto>> findAllOffers() {
        List<OfferResponseDto> allOffers = offerFacade.findAllOffers();
        return ResponseEntity.ok(allOffers);
    }

    @GetMapping("/offers/{offerId}")
    public ResponseEntity<OfferResponseDto> findOfferById(@PathVariable String offerId) {
        OfferResponseDto offerById = offerFacade.findOfferById(offerId);
        return ResponseEntity.ok(offerById);
    }

    @PostMapping("/offers")
    public ResponseEntity<CreateOfferResponseDto> createOffer(@RequestBody @Valid CreateOfferRequestControllerDto dto) {
        CreateOfferRequestDto createOfferRequestDto = mapFromCreateOfferRequestControllerDtoToCreateOfferRequestDto(dto);
        CreateOfferResponseDto createOfferResponseDto = offerFacade.saveOffer(createOfferRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createOfferResponseDto);
    }
}
