package com.franco.appnotes.security;

import lombok.Builder;

@Builder
public record AuthenticationRequest(String username, String password)
{}
