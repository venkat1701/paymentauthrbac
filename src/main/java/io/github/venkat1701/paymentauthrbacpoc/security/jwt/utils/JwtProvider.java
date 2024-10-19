package io.github.venkat1701.paymentauthrbacpoc.security.jwt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtProvider {

    @Value("{jwt.token.validity}")
    private static int EXPIRATION;

    @Value("{jwt.token.prefix}")
    private static String TOKEN_PREFIX;

    public String generateToken(Authentication authentication, Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION);
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("email", authentication.getName())
                .claim("userId", userId)
                .signWith(Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes()))
                .compact();
    }

    public String getEmailFromToken(String jwt) {
        try{
            if(jwt.startsWith(TOKEN_PREFIX)) {
                jwt = jwt.substring(TOKEN_PREFIX.length());
            }

            SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            return claims.get("email", String.class);
        } catch(Exception e) {
            throw new RuntimeException("Invalid JWT Token", e);
        }
    }

    public Long getUserIdFromToken(String jwt) {
        try{
            if(jwt.startsWith(TOKEN_PREFIX)) {
                jwt = jwt.substring(TOKEN_PREFIX.length());
            }

            SecretKey  key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            return claims.get("userId", Long.class);
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT Token", e);
        }
    }
}
