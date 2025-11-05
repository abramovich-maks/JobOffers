package com.joboffers.controller.error;

import com.joboffers.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OfferUrlDuplicateErrorIntegrationTest extends BaseIntegrationTest {

    @Container
    public static final MongoDBContainer mongoDBContainerForDuplicateTest = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainerForDuplicateTest::getReplicaSetUrl);
        registry.add("joboffers.offer-fetcher.http.client.config.uri", () -> WIRE_MOCK_HOST);
        registry.add("joboffers.offer-fetcher.http.client.config.port", () -> wireMockServer.getPort());
    }

    @Test
    public void should_return_409_conflict_when_offer_url_already_exists_in_database() throws Exception {
        // step1
        // given
        String jsonOffer = """
                {
                "title": "Junior Java Developer",
                "company": "Amelco Limited",
                "salary": "450-600 PLN/day on B2B",
                "offerUrl": "https://www.linkedin.com/jobs/view/4310359141"
                }
                """.trim();
        // when
        ResultActions perform1 = mockMvc.perform(post("/offers")
                .content(jsonOffer)
                .contentType(MediaType.APPLICATION_JSON));
        // then
        perform1.andExpect(status().isCreated());

        // step2
        // when
        ResultActions performSameOffer = mockMvc.perform(post("/offers")
                .content(jsonOffer)
                .contentType(MediaType.APPLICATION_JSON));
        // then
        performSameOffer.andExpect(status().isConflict());
    }
}
