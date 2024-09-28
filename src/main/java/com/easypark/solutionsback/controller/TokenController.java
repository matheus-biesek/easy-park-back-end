package com.easypark.solutionsback.controller;

import com.easypark.solutionsback.dto.request.EnumUserRoleRequestDTO;
import com.easypark.solutionsback.infra.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @GetMapping("/jwt-is-valid")
    public boolean tokenIsValid(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        return this.tokenService.booleanValidateToken(token);
    }

    @PostMapping("/jwt-is-valid-role")
    public boolean tokenIsValidRole(@RequestHeader("Authorization") String authHeader, @RequestBody EnumUserRoleRequestDTO body) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false;
        }
        String token = authHeader.substring(7);
        return this.tokenService.booleanValidateTokenForRole(token, body.getRole());
    }
}
