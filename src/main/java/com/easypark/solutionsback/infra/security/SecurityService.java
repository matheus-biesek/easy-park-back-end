package com.easypark.solutionsback.infra.security;

import com.easypark.solutionsback.dto.request.*;
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

    public Optional<String> login(LoginRequestDTO body) {
        User user = this.userRepository.findByUsername(body.getUsername()).orElse(null);
        if (user == null) {
            return Optional.empty();
        }
        if (passwordEncoder.matches(body.getPassword(), user.getPassword())) {
            return Optional.of(this.tokenService.generateToken(user));
        }
        return Optional.empty();
    }

    public String registerUser(RegisterUserRequestDTO body) {
        Optional<User> user = this.userRepository.findByUsername(body.getUsername());
        if (user.isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(body.getPassword()));
        newUser.setUsername(body.getUsername());
        newUser.setRole(EnumUserRole.USER);
        this.userRepository.save(newUser);
        return this.tokenService.generateToken(newUser);
    }

    public ResponseEntity<String> updateRoleUser(UpdateRoleRequestDTO body) {
        Optional<User> user = this.userRepository.findByUsername(body.getUsername());
        if(user.isPresent()) {
            user.get().setRole(body.getRole());
            this.userRepository.save(user.get());
            return ResponseEntity.ok("Role do atualizada com sucesso!");
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<String> deleteUser(UsernameRequestDTO body) {
        Optional<User> user = this.userRepository.findByUsername(body.getUsername());
        if(user.isPresent()) {
            this.userRepository.delete(user.get());
            return ResponseEntity.ok("Usuário deletado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não foi encontrado!");
    }
}
