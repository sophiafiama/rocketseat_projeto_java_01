package br.com.java01.rocketseat_projeto_java_1.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTProvider {
    @Value("${auth.security.token.secret}")
    private String secretKey;

    public DecodedJWT validateToken(String token) {
        token = token.replace("Bearer ", "");

        try {
            var tokenDecoded = JWT.require(Algorithm.HMAC256(secretKey))
                    .build()
                    .verify(token);

            return tokenDecoded;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
