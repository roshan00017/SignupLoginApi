package org.example.signuplogin.security.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.signuplogin.entity.User;
import org.example.signuplogin.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


    @Service()
    public class JwtServiceImpl implements JwtService {

        private static final int KEY_LENGTH = 64;
        @Value("${token.signing.key}")
        private String jwtSigningKey;

        @Override
        public String extractUserName(String token) {
            Claims claims = extractAllClaims(token);
            return claims.get("username", String.class);

        }

        @Override
        public String extractEmail(String token) {
            Claims claims = extractAllClaims(token);
            return claims.get("email", String.class);
        }
        @Override
        public String generateToken(User user) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", user.getDisplayName());
            claims.put("email",user.getEmail());
            return generateToken(claims);
        }

        @Override
        public String generateRefreshToken() {
            SecureRandom secureRandom = new SecureRandom();
            byte[] keyBytes = new byte[KEY_LENGTH];
            secureRandom.nextBytes(keyBytes);
            return Base64.getEncoder().encodeToString(keyBytes);
        }


        public boolean isTokenValid(String token, UserDetails userDetails) {
            final String userName = extractUserName(token);
            return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
        }

        private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
            final Claims claims = extractAllClaims(token);
            return claimsResolvers.apply(claims);
        }

        private String generateToken(Map<String, Object> extraClaims) {
            return Jwts.builder().setClaims(extraClaims)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
        }



        private boolean isTokenExpired(String token) {
            return extractExpiration(token).before(new Date());
        }

        private Date extractExpiration(String token) {
            return extractClaim(token, Claims::getExpiration);
        }

        private Claims extractAllClaims(String token) {
            return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                    .getBody();
        }

        private Key getSigningKey() {
            byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
            return Keys.hmacShaKeyFor(keyBytes);
        }
    }

