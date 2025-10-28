package com.joboffers.scheduler;

import com.joboffers.BaseIntegrationTest;
import com.joboffers.JobOffersApplication;
import com.joboffers.domain.offer.JobOfferFetchable;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = JobOffersApplication.class, properties = "scheduler.enabled=true")
public class HttpOffersSchedulerTest extends BaseIntegrationTest {

    @SpyBean
    JobOfferFetchable remoteOfferClient;

    @Test
    public void should_run_http_client_offers_fetching_exactly_given_times() {
        await()
                .atMost(Duration.ofSeconds(2))
                .untilAsserted(() -> verify(remoteOfferClient, times(2)).fetchOffers());

    }

}
