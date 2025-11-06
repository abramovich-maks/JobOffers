package com.joboffers.infrastructure.offer.http;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "job.offer.http.client.config")
@Builder
public record JobOffersRestTemplateConfigurationProperties(long connectionTimeout, long readTimeout, String uri, int port) {
}
