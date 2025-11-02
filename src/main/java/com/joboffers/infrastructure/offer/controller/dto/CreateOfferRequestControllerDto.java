package com.joboffers.infrastructure.offer.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.joboffers.infrastructure.apivalidation.ValidationConstants.COMPANY_MAX_SIZE;
import static com.joboffers.infrastructure.apivalidation.ValidationConstants.COMPANY_MIN_SIZE;
import static com.joboffers.infrastructure.apivalidation.ValidationConstants.TITLE_MAX_SIZE;
import static com.joboffers.infrastructure.apivalidation.ValidationConstants.TITLE_MIN_SIZE;


public record CreateOfferRequestControllerDto(
        @NotNull(message = "{title.not.null}")
        @NotEmpty(message = "{title.not.empty}")
        @Size(min = TITLE_MIN_SIZE, max = TITLE_MAX_SIZE, message = "{title.size}")
        String title,

        @NotNull(message = "{company.not.null}")
        @NotEmpty(message = "{company.not.empty}")
        @Size(min = COMPANY_MIN_SIZE, max = COMPANY_MAX_SIZE, message = "{company.size}")
        String company,

        String salary,

        @NotNull(message = "{offerUrl.not.null}")
        @NotEmpty(message = "{offerUrl.not.empty}")
        String offerUrl) {
}
