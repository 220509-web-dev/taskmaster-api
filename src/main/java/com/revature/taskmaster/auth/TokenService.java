package com.revature.taskmaster.auth;


import com.revature.taskmaster.auth.dtos.Principal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TokenService {

    private final JwtConfig jwtConfig;

    public TokenService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(Principal subject) {
        long now = System.currentTimeMillis();

        JwtBuilder tokenBuilder = Jwts.builder()
                                      .setId(subject.getAuthUserId())
                                      .setIssuer("taskmaster")
                                      .claim("role", subject.getAuthUserRole())
                                      .setIssuedAt(new Date(now))
                                      .setExpiration(new Date(now + jwtConfig.getExpiration()))
                                      .signWith(jwtConfig.getSigAlg(), jwtConfig.getSigningKey());

        return tokenBuilder.compact();

    }

    public Optional<Principal> extractTokenDetails(String token) {

        try {

            Claims claims = Jwts.parser()
                                .setSigningKey(jwtConfig.getSigningKey())
                                .parseClaimsJws(token)
                                .getBody();

            return Optional.of(new Principal(claims.getId(), claims.get("role", String.class)));

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }


}
