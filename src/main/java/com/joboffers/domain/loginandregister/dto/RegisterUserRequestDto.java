package com.joboffers.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record RegisterUserRequestDto (
        String mail,
        String password
){
}
