package br.com.java01.rocketseat_projeto_java_1.modules.auth.repository;

import br.com.java01.rocketseat_projeto_java_1.modules.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
