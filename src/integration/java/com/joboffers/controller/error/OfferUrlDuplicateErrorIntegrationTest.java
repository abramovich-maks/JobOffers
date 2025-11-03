package com.joboffers.controller.error;

import com.joboffers.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OfferUrlDuplicateErrorIntegrationTest extends BaseIntegrationTest {

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
