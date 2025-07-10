package com.example.demo.Utilities;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private String PRIVATE_KEY = "secretKey123";

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, PRIVATE_KEY)
                .compact();
    }
    public boolean validateToken(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }
    public Date extractExpiration(String token){
        return getClaimsFromToken(token).getExpiration();
    }
    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }
    public String extractUsername(String token){
        return getClaimsFromToken(token).getSubject();
    }
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(PRIVATE_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
