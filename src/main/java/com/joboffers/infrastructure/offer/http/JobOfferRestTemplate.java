package com.joboffers.infrastructure.offer.http;

import com.joboffers.domain.offer.JobOfferFetchable;
import com.joboffers.domain.offer.dto.GetOfferResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Log4j2
@AllArgsConstructor
public class JobOfferRestTemplate implements JobOfferFetchable {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public List<GetOfferResponseDto> fetchOffers() {
        log.info("Started fetching offers using http client");
        HttpHeaders headers = new HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        try {
            ResponseEntity<List<GetOfferResponseDto>> response = makeGetRequest(requestEntity);
            return getOffers(response);
        } catch (ResourceAccessException e) {
            log.error("Error while fetching offers using http client: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<GetOfferResponseDto> getOffers(final ResponseEntity<List<GetOfferResponseDto>> response) {
        List<GetOfferResponseDto> responseBody = response.getBody();
        if (responseBody == null) {
            log.error("Response Body was null");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        log.info("Success Response Body Returned: {}", response);
        return responseBody;
    }

    private ResponseEntity<List<GetOfferResponseDto>> makeGetRequest(final HttpEntity<HttpHeaders> requestEntity) {
        String urlForService = getUrlForService("/offers");
        final String url = UriComponentsBuilder.fromHttpUrl(urlForService).toUriString();
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
    }

    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }
}