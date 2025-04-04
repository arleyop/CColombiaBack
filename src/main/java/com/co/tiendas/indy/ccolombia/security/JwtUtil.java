package com.co.tiendas.indy.ccolombia.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "claveSecreta";
    private final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hora

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    public String extractUsername(String token) {
        return decodeToken(token).getSubject();
    }

    public boolean validateToken(String token, String username) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).withSubject(username).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    private DecodedJWT decodeToken(String token) {
        return JWT.require(algorithm).build().verify(token);
    }
}

