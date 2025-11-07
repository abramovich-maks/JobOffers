package com.joboffers.apivalidationerror;

import com.joboffers.BaseIntegrationTest;
import com.joboffers.infrastructure.apivalidation.ApiValidationErrorDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiValidationFailedIntegrationTest extends BaseIntegrationTest {

    @Test
    @WithMockUser
    public void should_return_400_bad_request_and_validation_message_when_request_has_empty_title() throws Exception {
        // given
        // when
        ResultActions perform = mockMvc.perform(post("/offers")
                .content("""
                        {
                            "title": "",
                            "company": "Amelco Limited",
                            "salary": "450-600 PLN/day on B2B",
                            "offerUrl": "https://www.linkedin.com/jobs/view/4310359141"
                        }
                        """.trim()
                )
                .contentType(MediaType.APPLICATION_JSON));
        // then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto result = objectMapper.readValue(json, ApiValidationErrorDto.class);
        assertThat(result.message()).containsExactlyInAnyOrder(
                "title must not be empty", "title must be between 3 and 30 characters"
        );
    }

    @Test
    @WithMockUser
    public void should_return_400_bad_request_and_validation_message_when_request_has_empty_company() throws Exception {
        // given
        // when
        ResultActions perform = mockMvc.perform(post("/offers")
                .content("""
                        {
                            "title": "Junior Java Developer",
                            "company": "",
                            "salary": "450-600 PLN/day on B2B",
                            "offerUrl": "https://www.linkedin.com/jobs/view/4310359141"
                        }
                        """.trim()
                )
                .contentType(MediaType.APPLICATION_JSON));
        // then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto result = objectMapper.readValue(json, ApiValidationErrorDto.class);
        assertThat(result.message()).containsExactlyInAnyOrder(
                "company must not be empty",
                "company must be between 5 and 100 characters"
        );
    }

    @Test
    @WithMockUser
    public void should_return_400_bad_request_and_validation_message_when_request_has_empty_offerUrl() throws Exception {
        // given
        // when
        ResultActions perform = mockMvc.perform(post("/offers")
                .content("""
                        {
                            "title": "Junior Java Developer",
                            "company": "Amelco Limited",
                            "salary": "450-600 PLN/day on B2B",
                            "offerUrl": ""
                        }
                        """.trim()
                )
                .contentType(MediaType.APPLICATION_JSON));
        // then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto result = objectMapper.readValue(json, ApiValidationErrorDto.class);
        assertThat(result.message()).containsExactlyInAnyOrder(
                "offerUrl must not be empty"
        );
    }

    @Test
    @WithMockUser
    public void should_return_400_bad_request_and_validation_message_when_request_does_not_have_title_and_company_and_url() throws Exception {
        // given
        // when
        ResultActions perform = mockMvc.perform(post("/offers")
                .content("""
                        {}
                        """.trim()
                )
                .contentType(MediaType.APPLICATION_JSON));
        // then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto result = objectMapper.readValue(json, ApiValidationErrorDto.class);
        assertThat(result.message()).containsExactlyInAnyOrder(
                "company must not be null",
                "company must not be empty",
                "offerUrl must not be null",
                "offerUrl must not be empty",
                "title must not be null",
                "title must not be empty"
        );
    }
}
