package br.com.java01.rocketseat_projeto_java_1.modules.courses.controller;

import br.com.java01.rocketseat_projeto_java_1.modules.courses.dto.AuthRequestDTO;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.dto.AuthResponseDTO;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.exceptions.UserFoundException;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.model.Course;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.model.User;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Autenticação")
public class AuthController {
    @Value("${auth.security.token.secret}")
    private String secretKey;

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    @Operation(summary = "Cadastrar usuário", description = "Cadastra um novo usuário")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário cadastrado", content = @Content(
            schema = @Schema(implementation = User.class))
        ),
    })
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        try {
            User result = register(user);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário", description = "Autentica um usuário existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário autenticado", content = @Content(
                schema = @Schema(implementation = AuthResponseDTO.class))
        ),
    })
    public ResponseEntity<?> auth(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            var token = authenticate(authRequestDTO);

            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private User register(User user){
        userRepository.findByEmail(user.getEmail())
                .ifPresent((u) -> {
                    throw new UserFoundException();
                });

        var password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        return this.userRepository.save(user);
    }

    private AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) throws AuthenticationException {
        var user = userRepository.findByEmail(authRequestDTO.email())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Username/password incorrect");
                });

        var passwordMatches = this.passwordEncoder.matches(authRequestDTO.password(), user.getPassword());
        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        var algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofMinutes(30));
        var token = JWT.create()
                .withIssuer("test-vagas")
                .withExpiresAt(expiresIn)
                .withSubject(user.getId().toString())
                .sign(algorithm);

        return AuthResponseDTO.builder()
                .accessToken(token)
                .expiresIn(expiresIn.toEpochMilli())
                .build();
    }
}
