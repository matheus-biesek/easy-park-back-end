package com.easypark.solutionsback.infra.security;

import com.easypark.solutionsback.model.User;
import com.easypark.solutionsback.enun.EnumUserRole;
import com.easypark.solutionsback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public Optional<String> login(String username, String password) {
        User user = this.userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return Optional.empty();
        }
        if (passwordEncoder.matches(password, user.getPassword())) {
            return Optional.of(this.tokenService.generateToken(user));
        }
        return Optional.empty();
    }

    public String registerUserWithRole(String username, String password, EnumUserRole role) {
        Optional<User> user = this.userRepository.findByUsername(username);
        if (user.isPresent()) {
            throw new RuntimeException("O usuário já existe!");
        }
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setUsername(username);
        newUser.setRole(role);
        this.userRepository.save(newUser);
        return this.tokenService.generateToken(newUser);
    }

    public ResponseEntity<String> updateRoleUser(String username, EnumUserRole role) {
        try {
            Optional<User> user = this.userRepository.findByUsername(username);
            if (user.isPresent()) {
                user.get().setRole(role);
                this.userRepository.save(user.get());
                return ResponseEntity.ok("Role atualizada com sucesso!");
            }
            return ResponseEntity.badRequest().body("Usuário não encontrado!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar a role do usuário.");
        }
    }

    public ResponseEntity<String> deleteUser(String username) {
        try {
            Optional<User> user = this.userRepository.findByUsername(username);
            if (user.isPresent()) {
                this.userRepository.delete(user.get());
                return ResponseEntity.ok("Usuário deletado com sucesso!");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar o usuário.");
        }
    }
}
