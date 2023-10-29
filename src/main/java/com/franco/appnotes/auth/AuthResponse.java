package com.franco.appnotes.auth;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AuthResponse(
        String jwt,
        UUID id,
        String username
//        Role role
) {

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "token='" + jwt + '\'' +
                ", id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
