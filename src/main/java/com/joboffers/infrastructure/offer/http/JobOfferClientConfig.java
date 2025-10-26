package com.joboffers.infrastructure.offer.http;

import com.joboffers.domain.offer.JobOfferFetchable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;


@Configuration
public class JobOfferClientConfig {

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(5000))
                .setReadTimeout(Duration.ofMillis(5000))
                .build();
    }


    @Bean
    public JobOfferFetchable remoteJobOfferClient(RestTemplate restTemplate,
                                                  @Value("${job.offers.http.client.config.uri}") String uri,
                                                  @Value("${job.offers.http.client.config.port}") int port) {
        return new JobOfferRestTemplate(restTemplate, uri, port);
    }
}