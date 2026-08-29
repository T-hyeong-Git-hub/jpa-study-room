package com.kkth.jpaStudyRoom.global.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long accessExpiration = 1000L * 60 * 30;

    private final long refreshExpiration = 1000L * 60 * 60 * 24 * 14;

    public String createAccessToken(Long memberId) {
        return createToken(
                memberId,
                accessExpiration,
                "access"
        );
    }

    public String createRefreshToken(Long memberId) {
        return createToken(
                memberId,
                refreshExpiration,
                "refresh"
        );
    }

    public String createToken(Long memberId, long expiration, String type) {
        return Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .claim("type",type)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public String getTokenType(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("type", String.class);
    }

    public Long getMemberId(String token) {
        return Long.parseLong(
                Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject()
        );
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
