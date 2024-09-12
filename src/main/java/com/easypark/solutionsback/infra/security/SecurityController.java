package com.easypark.solutionsback.infra.security;

import com.easypark.solutionsback.dto.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SecurityController {

    private final TokenService tokenService;
    private final SecurityService securityService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        return this.securityService.login(body);
    }

    @PostMapping("/register-client")
    public ResponseEntity registerUser(@RequestBody RegisterRequestDTO body){
        return this.securityService.registerUser(body);
    }

    @PostMapping("/register-adm")
    public ResponseEntity registerAdm(@RequestBody RegisterAdmRequestDTO body){
        return this.securityService.registerAdm(body);
    }

    @PutMapping("/update-role")
    public @ResponseBody ResponseEntity<String> updateRoleUser(@RequestBody RoleRequestDTO body){
        return this.securityService.updateRoleUser(body);
    }

    @PostMapping("/token-is-valid") // melhorar função e adicionar o serviço
    public boolean tokenIsValid(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        return this.tokenService.booleanValidateToken(token);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<String> deleteUser(@RequestBody DeleteUserRequestDTO body) {
        return this.securityService.deleteUser(body);
    }
}
