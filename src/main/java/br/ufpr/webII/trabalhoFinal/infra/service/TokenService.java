package br.ufpr.webII.trabalhoFinal.infra.service;

import br.ufpr.webII.trabalhoFinal.domain.user.User;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.TokenException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String getToken(User user) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("api-trabalho-final.ufpr.br")
                    .withSubject(user.getId().toString())
                    .withClaim("profile", user.getClass().getSimpleName())
                    .withClaim("name", user.getName())
                    .withExpiresAt(expireDate())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerar token jwt", exception);
        }
    }

    public String getProfile(String token) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("api-trabalho-final.ufpr.br")
                    .build()
                    .verify(token)
                    .getClaim("profile").asString();
        } catch (JWTVerificationException exception) {
            throw new TokenException("Token JWT inválido ou expirado!");
        }
    }

    public Long getUserId(String token) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return Long.parseLong(JWT.require(algoritmo)
                    .withIssuer("api-trabalho-final.ufpr.br")
                    .build()
                    .verify(token)
                    .getSubject());
        } catch (JWTVerificationException exception) {
            throw new TokenException("Token JWT inválido ou expirado!");
        }
    }

    private Instant expireDate() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }

}
