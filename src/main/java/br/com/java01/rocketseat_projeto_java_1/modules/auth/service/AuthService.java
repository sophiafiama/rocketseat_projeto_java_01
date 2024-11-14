package br.com.java01.rocketseat_projeto_java_1.modules.auth.service;

import br.com.java01.rocketseat_projeto_java_1.modules.auth.dto.AuthRequestDTO;
import br.com.java01.rocketseat_projeto_java_1.modules.auth.dto.AuthResponseDTO;
import br.com.java01.rocketseat_projeto_java_1.modules.auth.model.User;

import javax.naming.AuthenticationException;

public interface AuthService {
    User registerUser(User user);
    AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) throws AuthenticationException;
}
