package com.joboffers.http.error;

import com.joboffers.domain.offer.JobOfferFetchable;
import com.joboffers.infrastructure.offer.http.JobOfferClientConfig;
import com.joboffers.infrastructure.offer.http.JobOffersRestTemplateConfigurationProperties;
import com.joboffers.infrastructure.offer.http.RestTemplateResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

class OffersRestTemplateIntegrationTestConfig extends JobOfferClientConfig {

    public OffersRestTemplateIntegrationTestConfig(final JobOffersRestTemplateConfigurationProperties properties) {
        super(properties);
    }

    JobOfferFetchable remoteJobOfferClient() {
        RestTemplate restTemplate = restTemplate(new RestTemplateResponseErrorHandler());
        return remoteJobOfferClient(restTemplate);
    }
}
