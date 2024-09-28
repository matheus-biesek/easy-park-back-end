package com.easypark.solutionsback.dto.request;

import com.easypark.solutionsback.enun.EnumUserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnumUserRoleRequestDTO {
    @NotNull(message = "O gate do portão não pode ser nulo!")
    private EnumUserRole role;
}
