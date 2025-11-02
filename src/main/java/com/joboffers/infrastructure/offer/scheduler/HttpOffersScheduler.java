package com.joboffers.infrastructure.offer.scheduler;

import com.joboffers.domain.offer.OfferFacade;
import com.joboffers.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Log4j2
@Component
public class HttpOffersScheduler {

    private final OfferFacade offerFacade;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedDelayString = "${joboffers.offer-fetcher.http.scheduler.fetchingFrequency}")
    public List<OfferResponseDto> fetchAllOffersAndSaveIfNotExists() {
        log.info("Started offers fetching {}", dateFormat.format(new Date()));
        final List<OfferResponseDto> offers = offerFacade.fetchAllOffersAndSaveAllIfNotExists();
        log.info("Added new {} offers", offers.size());
        log.info("Stopped offers fetching {}", dateFormat.format(new Date()));
        return offers;
    }
}
