package org.itmo.infosec.infosec_lab1.service;

import org.itmo.infosec.infosec_lab1.dto.AuthResponse;
import org.itmo.infosec.infosec_lab1.dto.LoginRequest;
import org.itmo.infosec.infosec_lab1.dto.RegisterRequest;
import org.itmo.infosec.infosec_lab1.model.User;
import org.itmo.infosec.infosec_lab1.repository.UserRepository;
import org.itmo.infosec.infosec_lab1.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService tokenService;
    private final AuthenticationManager authManager;

    public AuthService(UserRepository users,
                       PasswordEncoder encoder,
                       JwtService tokenService,
                       AuthenticationManager authManager) {
        this.users = users;
        this.encoder = encoder;
        this.tokenService = tokenService;
        this.authManager = authManager;
    }

    public AuthResponse signUp(RegisterRequest req) {

        String email = req.email();
        if (users.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email exists");
        }

        User user = createUserFromRequest(req);
        users.save(user);

        return buildAuthResponse(user);
    }

    public AuthResponse signIn(LoginRequest req) {

        UsernamePasswordAuthenticationToken credentials =
                new UsernamePasswordAuthenticationToken(req.email(), req.password());

        try {
            authManager.authenticate(credentials);

            User user = users.findByEmail(req.email()).orElseThrow();
            return buildAuthResponse(user);
        } catch (AuthenticationException ex) {
            // тот же тип и сообщение — поведение полностью идентично
            throw new IllegalArgumentException("Wrong email/password");
        }
    }

    private User createUserFromRequest(RegisterRequest req) {
        User u = new User();
        u.setEmail(req.email());
        u.setName(req.name());
        u.setPassword(encoder.encode(req.password()));
        return u;
    }

    private AuthResponse buildAuthResponse(User user) {
        String token = tokenService.generateToken(user);
        return new AuthResponse(token);
    }
}
