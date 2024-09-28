package com.easypark.solutionsback.controller;

import com.easypark.solutionsback.dto.request.*;
import com.easypark.solutionsback.enun.EnumUserRole;
import com.easypark.solutionsback.infra.security.SecurityService;
import com.easypark.solutionsback.infra.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequestDTO body, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", errors));
        }
        Optional<String> token = this.securityService.login(body.getUsername(), body.getPassword());
        if (token.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("token", token.get());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Credenciais não são válidas!"));
        }
    }

    @PostMapping("/register-client")
    public ResponseEntity<Map<String, String>> registerUser(@Valid @RequestBody RegisterUserRequestDTO body, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", errors));
        }
        try {
            String token = this.securityService.registerUserWithRole(body.getUsername(), body.getPassword(), EnumUserRole.USER);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/register-adm")
    public ResponseEntity<Map<String, String>> registerAdm(@Valid @RequestBody RegisterUserRequestDTO body, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", errors));
        }
        try {
            String token = this.securityService.registerUserWithRole(body.getUsername(), body.getPassword(), EnumUserRole.ADMIN);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PutMapping("/update-role")
    public @ResponseBody ResponseEntity<String> updateRoleUser(@Valid @RequestBody UpdateRoleRequestDTO body, BindingResult result){
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errors);
        }
        return this.securityService.updateRoleUser(body.getUsername(), body.getRole());
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<String> deleteUser(@Valid @RequestBody UsernameRequestDTO body, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errors);
        }
        return this.securityService.deleteUser(body.getUsername());
    }
}
