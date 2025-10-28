package com.joboffers.infrastructure.offer.scheduler;

import com.joboffers.domain.offer.OfferFacade;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
@Log4j2
class HttpOffersSchedulerConfig {

    @Bean
    public HttpOffersScheduler httpOffersScheduler(OfferFacade offerFacade) {
        return new HttpOffersScheduler(offerFacade);
    }
}
