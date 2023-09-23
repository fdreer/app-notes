package com.franco.appnotes.security;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String token,
        Long id,
        String username
//        Role role
) {

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "token='" + token + '\'' +
                ", id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
