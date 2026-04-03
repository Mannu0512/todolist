package com.todolist.utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

 //private String SECRET= String.valueOf(Keys.secretKeyFor(SignatureAlgorithm.HS256));
 @Value("${jwt.secret}")
 private String secret;

 @Value("${jwt.expiration}")
 private long expiration;

 private SecretKey key;

    @PostConstruct
    public void init(){
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

 public String generateToken(String username){

     return Jwts.builder()
             .setSubject(username)
             .setIssuedAt(new Date())
             .setExpiration(new Date(System.currentTimeMillis()+expiration))
             .signWith(key, SignatureAlgorithm.HS256)
             .compact();
 }

   // extractUsername() → token se username nikalta hai
    public String extractUsername(String token){

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails){

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername());
    }
}