package br.com.java01.rocketseat_projeto_java_1.modules.auth.controller;

import br.com.java01.rocketseat_projeto_java_1.modules.auth.dto.AuthRequestDTO;
import br.com.java01.rocketseat_projeto_java_1.modules.auth.dto.AuthResponseDTO;
import br.com.java01.rocketseat_projeto_java_1.modules.auth.dto.CreateUserDTO;
import br.com.java01.rocketseat_projeto_java_1.modules.auth.exceptions.UserFoundException;
import br.com.java01.rocketseat_projeto_java_1.modules.auth.model.User;
import br.com.java01.rocketseat_projeto_java_1.modules.auth.repository.UserRepository;
import br.com.java01.rocketseat_projeto_java_1.modules.auth.service.AuthService;
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

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Cadastrar usuário", description = "Cadastra um novo usuário")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário cadastrado", content = @Content(
            schema = @Schema(implementation = User.class))
        ),
    })
    public ResponseEntity<?> create(@Valid @RequestBody CreateUserDTO createUserDTO) {
        try {
            User user = User.builder()
                    .email(createUserDTO.email())
                    .password(createUserDTO.password())
                    .name(createUserDTO.name())
                    .build();
            user = authService.registerUser(user);
            return ResponseEntity.ok().body(user);
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
}
