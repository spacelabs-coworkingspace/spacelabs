package com.rawlabs.spacelabs.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;

@Component
public class TokenProvider {

    @Value("${jwt.token.validity}")
    private Integer tokenExpiresIn;

    @Value("${jwt.signing.key}")
    private String jwtSigningKey;

    private Key key;

    @PostConstruct
    public void init() {
        final byte[] jwtSigningKeyBytes = Base64.getDecoder().decode(jwtSigningKey);
        key = new SecretKeySpec(jwtSigningKeyBytes, 0, jwtSigningKeyBytes.length, SignatureAlgorithm.HS256.getJcaName());
    }

    public String getUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public LocalDateTime getExpirationDate(String token) {
        Date expiresIn = getClaimFromToken(token, Claims::getExpiration);
        return expiresIn.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(Authentication authentication) {
        Date expiresIn = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(expiresIn);
        c.add(Calendar.HOUR, tokenExpiresIn);
        expiresIn = c.getTime();

        return Jwts.builder()
                .setSubject(authentication.getName())
                .signWith(key)
                .setIssuedAt(new Date())
                .setExpiration(expiresIn)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername()));
    }

    public Authentication getAuthenticationToken(final UserDetails userDetails) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

}
