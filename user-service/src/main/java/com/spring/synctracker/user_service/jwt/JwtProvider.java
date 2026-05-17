package com.spring.synctracker.user_service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtProvider {

    @Value("${secret.jwt}")
    private String jwtSecret;

    private SecretKey key;

    @PostConstruct
    private void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(Authentication authentication){

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String roles = populateAuthorities(authorities);

        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 1000 * 60 * 60))
                .setSubject(authentication.getName())
                .claim("authorities", roles)
                .claim("type", "access")
                .signWith(key)
                .compact();

        return jwt;

    }

    public String getEmailFromToken(String jwt){
        if(jwt.startsWith("Bearer ")){
            jwt = jwt.substring(7);
        }
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return String.valueOf(claims.getSubject());
    }

    public static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {

        Set<String> auths = new HashSet<>();

        for (GrantedAuthority authority : authorities) {
            auths.add(authority.getAuthority());
        }

        return String.join(",", auths);

    }

    public String generateRefreshToken(Authentication authentication){

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7))
                .setSubject(authentication.getName())
                .claim("type","refresh")
                .signWith(key)
                .compact();

    }

    public Claims extractClaims(String token){
        if(token.startsWith("Bearer ")){
            token = token.substring(7);
        }

        Claims claim = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claim;
    }





}
