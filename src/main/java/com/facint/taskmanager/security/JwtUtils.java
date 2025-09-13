package com.facint.taskmanager.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtUtils {

    private static final Logger logger = Logger.getLogger(JwtUtils.class.getName());

    @Value("${app.jwt.SecretKey}")
    private String jwtSecret;

    @Value("${app.jwt.ExpirationMs}")
    private Integer jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        Date currentTime = new Date();
        Date expirationTime = new Date(currentTime.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .issuedAt(currentTime)
                .expiration(expirationTime)
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)), Jwts.SIG.HS512)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        Claims claims = Jwts.parser()  // ✅ new entry point in 0.13.0
                .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8))) // replaces setSigningKey
                .build()
                .parseSignedClaims(token) // replaces parseClaimsJws
                .getPayload();

        return claims.getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(authToken);

            return true;
        } catch (ExpiredJwtException e) {
             logger.severe("JWT expired: " + e.getMessage());
        } catch (MalformedJwtException me) {
            logger.severe("Invalid JWT: " + me.getMessage());
        } catch (SignatureException se) {
            logger.severe("JWT signature validation failed: " + se.getMessage());
        } catch (IllegalArgumentException ie) {
            logger.severe("JWT token is null or empty: " + ie.getMessage());
        }

        return false;
    }
}
