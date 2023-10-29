package com.franco.appnotes.auth;

import com.franco.appnotes.security.RegisterRequest;

public interface IAuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(AuthRequest request);
}
