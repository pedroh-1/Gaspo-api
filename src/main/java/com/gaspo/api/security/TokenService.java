package com.gaspo.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gaspo.api.model.gaspo.FuncionarioModel;
import com.gaspo.api.model.gaspo.UsuarioModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret:my-secret-key-gaspo}")
    private String secret;

    public String generateToken(UserDetails userDetails) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String email = userDetails.getUsername();
            String nome = email;
            Long id = null;
            String tipo = "USUARIO";

            if (userDetails instanceof UsuarioModel usuario) {
                id = usuario.getId();
                nome = usuario.getNome();
            } else if (userDetails instanceof FuncionarioModel funcionario) {
                id = funcionario.getId();
                nome = funcionario.getNome();
                tipo = "FUNCIONARIO";
            }

            return JWT.create()
                    .withIssuer("gaspo-api")
                    .withSubject(email)
                    .withClaim("id", id)
                    .withClaim("nome", nome)
                    .withClaim("tipo", tipo)
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token jwt", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("gaspo-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
