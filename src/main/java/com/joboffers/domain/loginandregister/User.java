package com.joboffers.domain.loginandregister;

import lombok.Builder;

@Builder
record User(
        String userId,
        String mail,
        String password
) {
}
