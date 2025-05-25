package com.macro.authservice.service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.*;
import com.nimbusds.jwt.proc.*;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;

@Service
public class GoogleTokenVerifier {

    private static final String GOOGLE_JWK_URL = "https://www.googleapis.com/oauth2/v3/certs";
    private static final String CLIENT_ID = "916609941802-vnoruvntot40m1m100p8b0724dnj9k57.apps.googleusercontent.com";

    public JWTClaimsSet verify(String idToken) throws Exception {
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();

        JWKSource<SecurityContext> keySource = new RemoteJWKSet<>(new URL(GOOGLE_JWK_URL));
        JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(
                JWSAlgorithm.RS256, keySource
        );

        jwtProcessor.setJWSKeySelector(keySelector);

        JWTClaimsSet claims = jwtProcessor.process(idToken, null);

        List<String> audience = claims.getAudience();
        if (!audience.contains(CLIENT_ID)) {
            throw new SecurityException("Invalid audience: " + audience);
        }

        if (!Boolean.TRUE.equals(claims.getBooleanClaim("email_verified"))) {
            throw new SecurityException("Email not verified");
        }

        return claims;
    }
}
