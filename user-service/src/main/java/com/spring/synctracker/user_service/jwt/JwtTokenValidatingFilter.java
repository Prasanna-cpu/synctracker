package com.spring.synctracker.user_service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class JwtTokenValidatingFilter extends OncePerRequestFilter {

    @Value("${secret.jwt-header}")
    private String jwtHeader;

    @Value("${secret.jwt}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(jwtHeader);

        if(jwt != null && jwt.startsWith("Bearer ")){
            jwt = jwt.substring(7);

            try{
                SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                String type = (String) claims.get("type");

                if(!"access".equals(type)){
                    filterChain.doFilter(request, response);
                    return;
                }

                String email = String.valueOf(claims.getSubject());
                String authorities = String.valueOf(claims.get("authorities"));

                List<GrantedAuthority> grantedAuthoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, grantedAuthoritiesList);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            catch(ExpiredJwtException e){
                throw new BadCredentialsException("Token expired", e);
            }
            catch(Exception e){
                throw new BadCredentialsException("Invalid token", e);
            }

        }

        filterChain.doFilter(request, response);

    }
}
