package com.joboffers.infrastructure.offer.controller.error;

import org.springframework.http.HttpStatus;

public record OfferControllerResponseDto(
        String message,
        HttpStatus status) {
}

