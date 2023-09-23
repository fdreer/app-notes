package com.franco.appnotes.security;

import lombok.Builder;

@Builder
public record RegisterRequest(
        String username,
        String password,
        String Role
) {
}
