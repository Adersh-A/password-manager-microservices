package com.macro.authservice.controller;

import com.macro.authservice.dto.AuthResponse;
import com.macro.authservice.dto.GoogleAuthRequest;
import com.macro.authservice.service.GoogleTokenVerifier;
import com.macro.authservice.service.JwtService;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final GoogleTokenVerifier googleTokenVerifier;

    @PostMapping("/google")
    public ResponseEntity<?> authenticate(@RequestBody GoogleAuthRequest request) throws Exception {
        try {
            JWTClaimsSet claims = googleTokenVerifier.verify(request.getIdToken());
            String email = (String) claims.getClaim("email");
            String name = (String) claims.getClaim("name");
            String jwt = jwtService.generateToken(email);
            return ResponseEntity.ok(AuthResponse.builder().userName(name).jwt(jwt).build());
        } catch (Exception e) {
            return ResponseEntity.status(401).body(AuthResponse.builder().userName("Unauthorized").jwt(null).build());
        }
    }

}
