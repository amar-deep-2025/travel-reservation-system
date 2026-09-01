package com.travel.identity.service;

import com.travel.identity.entity.User;
import com.travel.identity.exception.JwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HexFormat;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user){
        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claim("tokenType","access")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+accessTokenExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(User user){

        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claim("tokenType","refresh")
                .issuedAt(new Date())
                .expiration(new Date(
                        System.currentTimeMillis()+refreshTokenExpiration
                ))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUserId(String token){

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String validateRefreshToken(String token){

        Claims claims=Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String tokenType=claims.get("tokenType", String.class);

        System.out.println("Token Type "+tokenType);

        if (!"refresh".equals(tokenType)){
            throw new JwtException("Invalid token type");
        }
        return  claims.getSubject();
    }

    public String hashToken(String token){

        try{
            MessageDigest digest=MessageDigest.getInstance("SHA-256");

            byte[] hash=digest.digest(token.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available ",e);
        }
    }

    public LocalDateTime extractExpiration(String token){

        Date expiration=Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return expiration.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

}
