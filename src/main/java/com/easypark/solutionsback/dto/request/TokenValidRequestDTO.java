package com.easypark.solutionsback.dto.request;

import com.easypark.solutionsback.enun.EnumUserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenValidRequestDTO {

    @NotBlank(message = "O token do usuário não pode ser nulo, vazio ou em branco!")
    @Size(min = 5, max = 100, message = "O token do usuário deve ter entre 5 e 100 caracteres!")
    private String token;

    @NotNull(message = "O papel do usuário não pode ser nulo!")
    private EnumUserRole role;
}
