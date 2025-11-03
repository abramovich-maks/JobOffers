package com.joboffers.domain.offer;

public class DuplicateKeyException extends RuntimeException {

    public final String offerUrl;

    public DuplicateKeyException(String offerUrl) {
        super(String.format("Offer with offerUrl [%s] already exists", offerUrl));
        this.offerUrl = offerUrl;
    }
}