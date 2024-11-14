package br.com.java01.rocketseat_projeto_java_1.modules.auth.service;

import br.com.java01.rocketseat_projeto_java_1.modules.auth.dto.AuthRequestDTO;
import br.com.java01.rocketseat_projeto_java_1.modules.auth.dto.AuthResponseDTO;
import br.com.java01.rocketseat_projeto_java_1.modules.auth.exceptions.UserFoundException;
import br.com.java01.rocketseat_projeto_java_1.modules.auth.model.User;
import br.com.java01.rocketseat_projeto_java_1.modules.auth.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;

@Service
public class AuthServiceImpl implements AuthService {
    @Value("${auth.security.token.secret}")
    private String secretKey;

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user){
        userRepository.findByEmail(user.getEmail())
                .ifPresent((u) -> {
                    throw new UserFoundException();
                });

        var password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        return this.userRepository.save(user);
    }

    public AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) throws AuthenticationException {
        var user = userRepository.findByEmail(authRequestDTO.email())
                .orElseThrow(() -> new UsernameNotFoundException("Username/password incorrect"));

        var passwordMatches = this.passwordEncoder.matches(authRequestDTO.password(), user.getPassword());
        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        var algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofMinutes(30));
        var token = JWT.create()
                .withIssuer("projeto-java-1")
                .withExpiresAt(expiresIn)
                .withSubject(user.getId().toString())
                .sign(algorithm);

        return AuthResponseDTO.builder()
                .accessToken(token)
                .expiresIn(expiresIn.toEpochMilli())
                .build();
    }
}
