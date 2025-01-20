package aluracursos.foro.forohub.security;

import aluracursos.foro.forohub.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class Tokens {
    @Value("${api.security.secret}")
    private String apiKey;

    private static final String ISSUER = "Foro Hub de AluraLatam";

    /**
     * Obtiene el asunto (subject) del token JWT después de validarlo.
     * @param token el token JWT a validar.
     * @return el asunto (subject) del token.
     */
    public String getSubject(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("El token proporcionado es nulo o vacío.");
        }

        try {
            DecodedJWT decodedJWT = JWT.require(getHMACAlgorithm())
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token);

            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            throw new IllegalArgumentException("El token proporcionado no es válido.", e);
        }
    }

    /**
     * Genera un token JWT para un usuario dado.
     * @param user el usuario para el que se generará el token.
     * @return el token JWT generado.
     */
    public String generateToken(User user) {
        if (user == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }

        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId())
                    .withExpiresAt(generateExpirationDate(24))
                    .sign(getHMACAlgorithm());
        } catch (JWTCreationException e) {
            throw new IllegalStateException("Error al generar el token JWT.", e);
        }
    }

    /**
     * Genera una fecha de expiración con base en las horas proporcionadas.
     * @param hours cantidad de horas hasta la expiración.
     * @return la fecha de expiración como un objeto `Date`.
     */
    private Date generateExpirationDate(int hours) {
        return Date.from(LocalDateTime.now()
                .plusHours(hours)
                .toInstant(ZoneOffset.of("-06:00")));
    }

    /**
     * Configura el algoritmo HMAC para la firma del token.
     *
     * @return el algoritmo configurado.
     */
    private Algorithm getHMACAlgorithm() {
        return Algorithm.HMAC256(apiKey);
    }
}
