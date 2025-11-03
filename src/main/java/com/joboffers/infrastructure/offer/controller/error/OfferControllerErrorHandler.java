package com.joboffers.infrastructure.offer.controller.error;

import com.joboffers.domain.offer.DuplicateKeyException;
import com.joboffers.domain.offer.OfferNotFoundException;
import com.joboffers.infrastructure.offer.controller.dto.OfferControllerResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;

@ControllerAdvice
@Log4j2
class OfferControllerErrorHandler {

    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public OfferControllerResponseDto handleOfferNotFoundException(OfferNotFoundException exception) {
        String message = exception.getMessage();
        log.error(message);
        return new OfferControllerResponseDto(message, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public OfferPostErrorResponse offerDuplicate(DuplicateKeyException duplicateKeyException) {
        final String message = String.format("Offer with offerUrl [%s] already exists", duplicateKeyException.offerUrl);
        log.error(message);
        return new OfferPostErrorResponse(Collections.singletonList(message), HttpStatus.CONFLICT);
    }
}
