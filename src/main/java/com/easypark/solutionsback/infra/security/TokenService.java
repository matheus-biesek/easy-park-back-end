package com.easypark.solutionsback.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
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
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
            logger.info("Token gerado para o usuário: {}", user.getUsername());
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao autenticar usuário!", exception);
        }
    }

    public String validateToken(String token) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token inválido ou expirado", exception);
        }
    }

    public boolean booleanValidateToken(String token) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            if (isTokenExpired(decodedJWT)) {
                logger.info("Token expirado - {}", token);
                return false;
            }
            logger.info("Token validado com sucesso - {}", token);
            return true;
        } catch (JWTVerificationException e) {
            logger.info("Erro ao validar token: {}", e.getMessage());
            return false;
        }
    }

    public boolean booleanValidateTokenForRole(String token, EnumUserRole role) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            if (isTokenExpired(decodedJWT)) {
                logger.info("Token expirado - {}", token);
                return false;
            }
            return isUserRoleValid(decodedJWT.getSubject(), role);
        } catch (JWTVerificationException e) {
            logger.info("Erro ao validar token: {}", e.getMessage());
            return false;
        }
    }

    private DecodedJWT verifyToken(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("login-auth-ipa")
                .build()
                .verify(token);
    }

    private boolean isTokenExpired(DecodedJWT decodedJWT) {
        Date expiration = decodedJWT.getExpiresAt();
        return expiration != null && expiration.before(new Date());
    }

    private boolean isUserRoleValid(String username, EnumUserRole role) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            logger.info("Usuário não encontrado: {}", username);
            return false;
        }
        return userOptional.get().getRole() == role;
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
