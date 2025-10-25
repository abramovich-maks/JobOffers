package com.joboffers.domain.loginandregister;

public class UserNotFoundException extends RuntimeException {

    private final String userEmail;

    public UserNotFoundException(String userEmail) {
        super(String.format("User with email %s not found", userEmail));
        this.userEmail = userEmail;
    }
}