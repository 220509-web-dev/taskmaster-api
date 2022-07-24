package com.revature.taskmaster.auth;


import com.revature.taskmaster.auth.dtos.Principal;
import com.revature.taskmaster.common.util.exceptions.MissingAuthTokenException;
import com.revature.taskmaster.common.util.exceptions.TokenParseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    private final JwtConfig jwtConfig;
    private final AuthRepository authRepo;

    public TokenService(JwtConfig jwtConfig, AuthRepository authRepo) {
        this.jwtConfig = jwtConfig;
        this.authRepo = authRepo;
    }

    public String generateToken(Principal subject) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                   .setId(subject.getAuthUserId())
                   .setIssuer("taskmaster")
                   .claim("username", subject.getAuthUsername())
                   .claim("role", subject.getAuthUserRole())
                   .setIssuedAt(new Date(now))
                   .setExpiration(new Date(now + jwtConfig.getExpiration()))
                   .signWith(jwtConfig.getSigningKey(), jwtConfig.getSigAlg())
                   .compact();

    }

    public Principal extractTokenDetails(String token) {

        if (token == null || token.isEmpty()) {
            throw new MissingAuthTokenException();
        }

        try {

            Claims claims = Jwts.parserBuilder()
                                .setSigningKey(jwtConfig.getSigningKey())
                                .build()
                                .parseClaimsJws(token)
                                .getBody();

            if (!authRepo.existsById(claims.getId())) {
                throw new TokenParseException("Unknown user id found in request token");
            }

            return new Principal(claims.getId(), claims.get("username", String.class), claims.get("role", String.class));

        } catch (ExpiredJwtException e) {
            throw new TokenParseException("The provided token is expired", e);
        } catch (Exception e) {
            throw new TokenParseException(e);
        }
    }

    public void checkToken(String token) throws TokenParseException {
        extractTokenDetails(token);
    }

}
