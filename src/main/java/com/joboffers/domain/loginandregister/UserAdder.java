package com.joboffers.domain.loginandregister;

import com.joboffers.domain.loginandregister.dto.RegisterUserRequestDto;
import com.joboffers.domain.loginandregister.dto.RegisterUserResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2
class UserAdder {

    private final UserRepository userRepository;

    RegisterUserResponseDto register(final RegisterUserRequestDto user) {
        if (userExists(user.mail())) {
            log.warn("User with email: {} already exists", user.mail());
            throw new UserAlreadyExistException(user.mail());
        }
        User createdUser = User.builder()
                .mail(user.mail())
                .password(user.password())
                .build();
        User savedUser = userRepository.save(createdUser);
        log.info("Saved user with id: {}", savedUser.userId());
        return RegisterUserResponseDto
                .builder()
                .mail(createdUser.mail())
                .message("Success. User created.")
                .build();
    }

    public boolean userExists(final String username) {
        return userRepository.existsByEmail(username);
    }
}

