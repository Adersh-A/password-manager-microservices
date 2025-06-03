package com.macro.authservice.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

@Service
public class JwtService {

    private RSAPrivateKey privateKey;

    @PostConstruct
    public void loadPrivateKey() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("keys/private_key.der");
        if (inputStream == null) {
            throw new IllegalStateException("Public key file not found ");
        }
        byte[] keyBytes = inputStream.readAllBytes();
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        this.privateKey = (RSAPrivateKey) kf.generatePrivate(spec);
    }

    public String generateToken(String email) throws JOSEException {
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(email)
                .claim("email", email)
                .issuer("auth-service")
                .issueTime(new Date()).expirationTime(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hour
                .build();

        JWSSigner signer = new RSASSASigner(privateKey);

        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(), claims);

        signedJWT.sign(signer);

        return signedJWT.serialize();
    }
}
