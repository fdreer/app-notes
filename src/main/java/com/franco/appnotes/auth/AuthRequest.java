package com.franco.appnotes.auth;

import lombok.Builder;

@Builder
public record AuthRequest(String username, String password)
{}
