package com.joboffers.infrastructure.security.jwt;

import com.joboffers.infrastructure.loginandregister.controller.dto.JwtResponseDto;
import com.joboffers.infrastructure.loginandregister.controller.dto.TokenRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
@AllArgsConstructor
public class JwtAuthenticatorFacade {

    private final AuthenticationManager authenticationManager;

    public JwtResponseDto authenticateAndGenerateToken(final @Valid TokenRequestDto loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.password()));
        return JwtResponseDto.builder().build();
    }
}

