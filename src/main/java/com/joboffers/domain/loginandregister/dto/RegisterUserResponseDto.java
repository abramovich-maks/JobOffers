package com.joboffers.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record RegisterUserResponseDto (
        String mail,
        String message
){
}
