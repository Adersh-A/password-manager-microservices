package com.macro.authservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

@Service
public class JwtService {

    private PrivateKey privateKey;

    @PostConstruct
    public void loadPrivateKey() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("keys/private_key.der");
        byte[] keyBytes = inputStream.readAllBytes();
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        this.privateKey = kf.generatePrivate(spec);
    }

    public String generateToken(String email,String name) {
        return Jwts.builder()
                .setSubject(email)
                .claim("name",name)
                .setIssuer("auth-service")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hour
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }
}
