package com.joboffers.domain.loginandregister;


import java.util.List;
import java.util.Optional;

interface UserRepository {

    Optional<User> findFirstByEmail(String email);

    User save(User user);

    boolean existsByEmail(String email);

    List<User> findAllUsers();
}

