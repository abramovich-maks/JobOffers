package com.joboffers.domain.loginandregister;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

class InMemoryLoginAndRegisterFacadeTestImpl implements UserRepository {

    Map<Long, User> inMemoryDatabase = new ConcurrentHashMap<>();
    AtomicInteger index = new AtomicInteger(1);


    @Override
    public Optional<User> findFirstByEmail(final String email) {
        return inMemoryDatabase.values().stream()
                .filter(user -> user.mail().equals(email)).findFirst();
    }

    @Override
    public User save(final User user) {
        long index = this.index.getAndIncrement();
        inMemoryDatabase.put(index, user);
        return User.builder()
                .userId(String.valueOf(index))
                .mail(user.mail())
                .build();
    }

    @Override
    public boolean existsByEmail(final String email) {
        long count = inMemoryDatabase.values().stream()
                .filter(user -> user.mail().equals(email))
                .count();
        return count == 1;
    }

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(inMemoryDatabase.values());
    }
}
