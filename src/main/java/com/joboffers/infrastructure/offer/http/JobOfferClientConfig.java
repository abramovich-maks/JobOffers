package com.joboffers.infrastructure.offer.http;

import com.joboffers.domain.offer.JobOfferFetchable;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;


@Configuration
@AllArgsConstructor
public class JobOfferClientConfig {

    private final JobOffersRestTemplateConfigurationProperties properties;

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(properties.connectionTimeout()))
                .setReadTimeout(Duration.ofMillis(properties.readTimeout()))
                .build();
    }


    @Bean
    public JobOfferFetchable remoteJobOfferClient(RestTemplate restTemplate) {
        return new JobOfferRestTemplate(restTemplate, properties.uri(), properties.port());
    }
}