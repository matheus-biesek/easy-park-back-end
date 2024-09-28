package com.easypark.solutionsback.dto.request;

import com.easypark.solutionsback.enun.EnumUserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateRoleRequestDTO {

    @NotBlank(message = "O nome de usuário não pode ser nulo, vazio ou em branco!")
    String username;

    @NotNull(message = "O papel de usuário não pode ser nulo!")
    EnumUserRole role;
}
