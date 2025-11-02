package com.joboffers.infrastructure.offer.controller.dto;

import org.springframework.http.HttpStatus;

public record OfferControllerResponseDto(
        String message,
        HttpStatus status) {
}

