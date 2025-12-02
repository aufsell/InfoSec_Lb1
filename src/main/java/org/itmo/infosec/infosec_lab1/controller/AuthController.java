package org.itmo.infosec.infosec_lab1.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.itmo.infosec.infosec_lab1.dto.AuthResponse;
import org.itmo.infosec.infosec_lab1.dto.LoginRequest;
import org.itmo.infosec.infosec_lab1.dto.RegisterRequest;
import org.itmo.infosec.infosec_lab1.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService auth;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        AuthResponse result = auth.signUp(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        AuthResponse result = auth.signIn(request);
        return ResponseEntity.ok(result);
    }
}
