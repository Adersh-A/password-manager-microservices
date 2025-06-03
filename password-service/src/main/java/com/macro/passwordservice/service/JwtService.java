package com.macro.passwordservice.service;

import com.macro.passwordservice.exception.ResourceNotFoundException;
import com.macro.passwordservice.exception.UnauthorizedAccessException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;

@Service
public class JwtService {

    private PublicKey publicKey;

    @PostConstruct
    public void loadPublicKey() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("keys/public_key.der");
        if (inputStream == null) {
            throw new ResourceNotFoundException("Public key file not found");
        }
        byte[] publicKeyBytes = inputStream.readAllBytes();
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
        publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    public boolean isTokenValid(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
            return signedJWT.verify(verifier);
        } catch (Exception  e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            return claims.getStringClaim("email");
        } catch (ParseException e) {
            throw new UnauthorizedAccessException("Invalid JWT format", e);
        }
    }

    public String extractSubject(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            throw new ResourceNotFoundException("Failed to extract subject from token", e);
        }
    }
}
