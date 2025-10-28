package com.joboffers.domain.offer;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Document("offers")
record Offer(
        @Id String offerId,
        @Field("position") String title,
        @Field("company") String company,
        @Field("salary") String salary,
        @Field("url") @Indexed(unique = true) String offerUrl
) {
}

