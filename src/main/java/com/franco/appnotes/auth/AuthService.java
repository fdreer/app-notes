package com.franco.appnotes.auth;

import com.franco.appnotes.security.RegisterRequest;
import com.franco.appnotes.security.jwt.JwtService;
import com.franco.appnotes.users.IUserService;
import com.franco.appnotes.users.dto.UserResponseDto;
import com.franco.appnotes.users.entities.Role;
import com.franco.appnotes.users.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService{
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;

    @Override
    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        UserResponseDto userCreated = userService.save(user);
        var jwt = jwtService.generateToken(user);

        return AuthResponse.builder()
                .jwt(jwt)
                .id(userCreated.getId())
                .username(userCreated.getUsername())
//                .role(Role.valueOf(userCreated.getRole()))
                .build();
    }

    @Override
    public AuthResponse login(AuthRequest request) {
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

        UserResponseDto userDTO = userService.findByUsername(request.username());

        return AuthResponse.builder()
                .username(userDTO.getUsername())
                .id(userDTO.getId())
                .jwt(jwt)
//                .role(Role.valueOf(userDTO.getRole()))
                .build();
    }
}
