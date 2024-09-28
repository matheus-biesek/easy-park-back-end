package com.easypark.solutionsback.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.easypark.solutionsback.enun.EnumUserRole;
import com.easypark.solutionsback.model.User;
import com.easypark.solutionsback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Value("${api.security.token.secret}")
    private String secret;

    private final UserRepository userRepository;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("login-auth-ipa")
                    .withSubject(user.getUsername())
                    .withClaim("role", user.getRole().ordinal())
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);
            logger.info("Token gerado para o usuário: {}", user.getUsername());
            logger.info("Token gerado: {}", token);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro enquanto está autenticando!", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("login-auth-ipa")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token inválido ou expirado", exception);
        }
    }

    public boolean booleanValidateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("login-auth-ipa")
                    .build()
                    .verify(token);

            // Verifica se o token já expirou
            Date expiration = decodedJWT.getExpiresAt();
            if (expiration != null && expiration.before(new Date())) {
                logger.info("Token expirado - {}", token);
                return false;
            }

            logger.info("Token validado com sucesso - {}", token);
            return true;
        } catch (TokenExpiredException e) {
            logger.info("Token expirado - {}", token);
            return false;
        } catch (JWTVerificationException exception) {
            logger.info("Token não é válido! - {}", token);
            return false;
        }
    }

    public boolean booleanValidateTokenAdm(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("login-auth-ipa")
                    .build()
                    .verify(token);

            // Verifica se o token já expirou
            Date expiration = decodedJWT.getExpiresAt();
            if (expiration != null && expiration.before(new Date())) {
                logger.info("Token expirado - {}", token);
                return false;
            }

            String username = decodedJWT.getSubject();
            User user = this.userRepository.findByUsername(username).orElse(null);

            if (user == null) {
                return false;
            }

            return user.getRole() == EnumUserRole.ADMIN;
        } catch (TokenExpiredException e) {
            logger.info("Token expirado - {}", token);
            return false;
        } catch (JWTVerificationException exception) {
            logger.info("Token não é válido! - {}", token);
            return false;
        }
    }


    public boolean booleanValidateTokenUser(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("login-auth-ipa")
                    .build()
                    .verify(token);

            // Verifica se o token já expirou
            Date expiration = decodedJWT.getExpiresAt();
            if (expiration != null && expiration.before(new Date())) {
                logger.info("Token expirado - {}", token);
                return false;
            }

            String username = decodedJWT.getSubject();
            User user = this.userRepository.findByUsername(username).orElse(null);

            if (user == null) {
                return false;
            }

            // Verifica se a role do usuário é USER
            return user.getRole() == EnumUserRole.USER;
        } catch (TokenExpiredException e) {
            logger.info("Token expirado - {}", token);
            return false;
        } catch (JWTVerificationException exception) {
            logger.info("Token não é válido! - {}", token);
            return false;
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
