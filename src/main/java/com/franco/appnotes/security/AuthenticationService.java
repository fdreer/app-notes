package com.franco.appnotes.security;

import com.franco.appnotes.dto.UserDtoResponse;
import com.franco.appnotes.entity.Role;
import com.franco.appnotes.entity.User;
import com.franco.appnotes.security.jwt.JwtService;
import com.franco.appnotes.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        UserDtoResponse userCreated = userService.save(user);
        var jwt = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwt)
                .id(userCreated.getId())
                .username(userCreated.getUsername())
//                .role(Role.valueOf(userCreated.getRole()))
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        final UserDetails user = userDetailsService.loadUserByUsername(
                request.username()
        );

        var jwt = jwtService.generateToken(user);

        UserDtoResponse userDTO = userService.findByUsername(request.username());

        return AuthenticationResponse.builder()
                .username(userDTO.getUsername())
                .id(userDTO.getId())
                .token(jwt)
//                .role(Role.valueOf(userDTO.getRole()))
                .build();
    }
}
