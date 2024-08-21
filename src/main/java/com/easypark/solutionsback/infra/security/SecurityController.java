package com.easypark.solutionsback.infra.security;

import com.easypark.solutionsback.dto.*;
import com.easypark.solutionsback.model.User;
import com.easypark.solutionsback.model.UserRole;
import com.easypark.solutionsback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SecurityController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        User user = this.userRepository.findByUsername(body.username()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new TokenResponseDTO(token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register-client")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<User> user = this.userRepository.findByUsername(body.username());
        if(user.isEmpty()){
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setUsername(body.username());
            newUser.setRole(UserRole.USER);
            this.userRepository.save(newUser);
            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new TokenResponseDTO(token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register-adm")
    public ResponseEntity registerAdm(@RequestBody RegisterAdmRequestDTO body){
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

    @PutMapping("/update-role")
    public @ResponseBody ResponseEntity updateRole(@RequestBody RoleRequestDTO body){
        Optional<User> user = this.userRepository.findByUsername(body.username());
        if(user.isPresent()) {
            user.get().setRole(body.role());
            this.userRepository.save(user.get());
            return ResponseEntity.ok(new StringResponseDTO("Role do atualizada com sucesso!"));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/token-is-valid")
    public boolean tokenIsValid(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        return this.tokenService.booleanValidateToken(token);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<StringResponseDTO> deleteUser(@RequestBody DeleteUserRequestDTO body) {
        Optional<User> user = this.userRepository.findByUsername(body.username());
        if(user.isPresent()) {
            this.userRepository.delete(user.get());
            return ResponseEntity.ok(new StringResponseDTO("Usuário deletado com sucesso!"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StringResponseDTO("Usuário não foi encontrado!"));
    }
}
