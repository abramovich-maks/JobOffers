package com.joboffers.infrastructure.offer.http;

import com.joboffers.domain.offer.JobOfferFetchable;
import com.joboffers.domain.offer.dto.GetOfferResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Log4j2
@AllArgsConstructor
public class JobOfferRestTemplate implements JobOfferFetchable {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    //    http://ec2-3-127-218-34.eu-central-1.compute.amazonaws.com:5057/offers
    @Override
    public List<GetOfferResponseDto> fetchOffers() {
        String urlForService = getUrlForService("/offers");
        HttpHeaders headers = new HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final String url = UriComponentsBuilder.fromHttpUrl(urlForService)
                .toUriString();
        ResponseEntity<List<GetOfferResponseDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        List<GetOfferResponseDto> jobOffers = response.getBody();
        log.info(jobOffers);
        return jobOffers;
    }

    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }
}