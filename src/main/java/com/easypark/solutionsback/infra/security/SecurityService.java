package com.easypark.solutionsback.infra.security;

import com.easypark.solutionsback.dto.request.*;
import com.easypark.solutionsback.dto.response.StringResponseDTO;
import com.easypark.solutionsback.dto.response.TokenResponseDTO;
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

    public ResponseEntity<TokenResponseDTO> login(LoginRequestDTO body) {
        User user = this.userRepository.findByUsername(body.username()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new TokenResponseDTO(token));
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<TokenResponseDTO> registerUser(RegisterRequestDTO body) {
        Optional<User> user = this.userRepository.findByUsername(body.username());
        if(user.isEmpty()){
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setUsername(body.username());
            newUser.setRole(EnumUserRole.USER);
            this.userRepository.save(newUser);
            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new TokenResponseDTO(token));
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<TokenResponseDTO> registerAdm(RegisterAdmRequestDTO body) {
        Optional<User> user = this.userRepository.findByUsername(body.username());
        if(user.isEmpty()){
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setUsername(body.username());
            newUser.setRole(body.role());
            this.userRepository.save(newUser);
            return ResponseEntity.ok(new TokenResponseDTO(tokenService.generateToken(newUser)));
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<StringResponseDTO> updateRoleUser(RoleRequestDTO body) {
        Optional<User> user = this.userRepository.findByUsername(body.username());
        if(user.isPresent()) {
            user.get().setRole(body.role());
            this.userRepository.save(user.get());
            return ResponseEntity.ok(new StringResponseDTO("Role do atualizada com sucesso!"));
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<StringResponseDTO> deleteUser(DeleteUserRequestDTO body) {
        Optional<User> user = this.userRepository.findByUsername(body.username());
        if(user.isPresent()) {
            this.userRepository.delete(user.get());
            return ResponseEntity.ok(new StringResponseDTO("Usuário deletado com sucesso!"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StringResponseDTO("Usuário não foi encontrado!"));
    }
}
