package com.joboffers.domain.loginandregister;

import com.joboffers.domain.loginandregister.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@AllArgsConstructor
@Log4j2
class UserRetriever {

    private final UserRepository userRepository;

    UserDto findByEmail(final String email) {
        User userByEmail = userRepository.findFirstByEmail(email)
                .orElseThrow(() -> {
                    log.warn("User with email: {} not found", email);
                    return new UserNotFoundException(email);
                });
        return UserDto.builder()
                .userId(userByEmail.userId())
                .mail(userByEmail.mail())
                .password(userByEmail.password())
                .build();
    }

    List<UserDto> findAllUsers() {
        List<User> allUsers = userRepository.findAllUsers();
        return allUsers.stream()
                .map(user -> UserDto.builder()
                        .userId(user.userId())
                        .mail(user.mail())
                        .password(user.password())
                        .build())
                .toList();
    }
}
